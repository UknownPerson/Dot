package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.Dot.Client;
import xyz.Dot.module.Category;
import xyz.Dot.module.Client.ClickGui;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.shader.BloomUtil;
import xyz.Dot.utils.shader.GaussianBlur;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;

import static org.lwjgl.opengl.GL11.*;

public class ClickUI extends GuiScreen {
    CFontRenderer font = FontLoaders.normalfont16; // 字体
    CFontRenderer font1 = FontLoaders.normalfont12; // 字体
    static Picker picker; // 调色板
    int windowX, windowY; // 窗口大小
    int width, height; // ClickGui大小
    float x, y = RenderUtils.height(); // ClickGui位置
    int xend, yend; // ClickGui位置终
    boolean keydown = false, keydown1 = false; // 是否按下左键、右键
    int check = 0; // (0 null) (1 X,Y) (2combat 3movemnt 4player 5render 6保留 curtype) (7 moduletoggle) (8 module setting) (9 custom) (10 setting)
    int keydownX, keydownY; // 按下左键时的X Y
    float beterspeedinfps; // 动画帧率修补量
    boolean anithisryaninend = false; //进入动画Y轴是否结束
    float alpha = 5; //背景初始亮度
    public static boolean toclose = false; //关闭动画所需参数 还有bug暂时没做好先留个接口
    float[] typeanimto = new float[8]; // 类别动画目标位置
    Module togglemodule; // 开关module暂存
    float rxendanim = 0;
    float customx = RenderUtils.width();
    boolean customend = false;
    static float tempdy, dy;
    long time;
    String check10setting;
    float tempy1 = 0,dy1 = 0;

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        //toclose = true;
        picker = null;
        Client.instance.modulemanager.getModuleByName("ClickGui").setToggle(false);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if (!Client.instance.modulemanager.getModuleByName("ClickGui").isToggle()) {
            this.mc.displayGuiScreen(null);
        }

        beterspeedinfps = 120.0f / Minecraft.getDebugFPS();

        windowX = RenderUtils.width();
        windowY = RenderUtils.height();

        float alphato;
        if (!toclose) {
            alphato = 128;
        } else {
            alphato = 5;
        }

        alpha = toanim(alpha, alphato, 32, 0.1f);
        RenderUtils.drawRect(0, 0, windowX, windowY, new Color(0, 0, 0, (int) alpha).getRGB());

        width = (int) ClickGui.width;
        height = (int) ClickGui.height;

        float anithisryto;
        if (!toclose) {
            anithisryto = ClickGui.y;
        } else {
            anithisryto = RenderUtils.height();
        }

        x = ClickGui.x;
        //x = toanim(x, ClickGui.x, 2, 1f);
        float animgety = 0;
        if (!anithisryaninend || toclose) {
            animgety = toanim(y, anithisryto, 8, 1f);
            if (anithisryto == animgety) {
                anithisryaninend = true;
            } else {
                y = animgety;
            }
        } else {
            y = anithisryto;
            //y = toanim(y, ClickGui.y, 2, 1f);
        }

        float rx = x;
        float ry = y;
        float blueheight = 12;
        xend = (int) (rx + width);
        yend = (int) (ry + height);

        RenderUtils.drawHalfRoundRect((int) rx, (int) ry, xend, (int) (ry + 5), 4, CustomColor.getColor());
        RenderUtils.drawRect((int) rx, (int) ry + 5, xend, (int) ry + 10, new Color(200, 200, 200).getRGB());
        RenderUtils.drawRoundRect((int) rx, (int) ry + 5, xend, yend, 4, new Color(200, 200, 200));
        //RenderUtils.drawRect((int) rx, (int) (ry + blueheight - 5), xend, (int) (ry + blueheight), CustomColor.getColor().getRGB());

        {
            float alphafuckk = 1024;
            int addy = 0;
            while (alphafuckk != 0) {
                alphafuckk = RenderUtils.toanimNoFps(alphafuckk, 0, 16, 1f);
                float alphafuck = (int) (255 * (alphafuckk / 1024));
                RenderUtils.drawRect((int) rx, (int) (ry + 5 + addy), xend, (int) (ry + 6 + addy), new Color(CustomColor.getColor().getRed(), CustomColor.getColor().getGreen(), CustomColor.getColor().getBlue(), (int) alphafuck).getRGB());
                addy++;
            }
        }


        String thisnametext = "ClickGui";
        FontLoaders.normalfont20.drawString(thisnametext, rx + 5, ry + 4, new Color(255, 255, 255).getRGB());

        rx += font1.getStringWidth(thisnametext) + 32;

        float fontrendery = y + (blueheight - font1.getStringHeight(ClickGui.curType.name())) / 2 + 1;
        for (Category c : Category.values()) {
            //RenderUtils.drawRect((int) rx, (int) ry, (int) (rx + 0.3f * width), (int) (ry + 0.0625f * height), new Color(255, 255, 255).getRGB());
            font1.drawString(c.name(), rx, fontrendery, new Color(255, 255, 255).getRGB());

            if (c == ClickGui.curType) {
                typeanimto[0] = rx - 4 - x;
                typeanimto[1] = rx + font1.getStringWidth(c.name()) + 4 - x;
            }

            float typeanimtomid = (typeanimto[0] + typeanimto[1]) / 2;
            typeanimto[0] = typeanimtomid - 5;
            typeanimto[1] = typeanimtomid + 5;

            if (ClickGui.typeanimx[0] == 0.0) {
                ClickGui.typeanimx[0] = typeanimto[0];
            }
            if (ClickGui.typeanimx[1] == 0.0f) {
                ClickGui.typeanimx[1] = typeanimto[1];
            }

            //last
            if (c == ClickGui.lastcurType) {
                typeanimto[3] = rx - 4 - x;
                typeanimto[4] = rx + font1.getStringWidth(c.name()) + 4 - x;
            }

            float lasttypeanimtomid = (typeanimto[3] + typeanimto[4]) / 2;
            typeanimto[3] = lasttypeanimtomid - 5;
            typeanimto[4] = lasttypeanimtomid + 5;

            if (ClickGui.typeanimx[3] == 0.0) {
                ClickGui.typeanimx[3] = typeanimto[3];
            }
            if (ClickGui.typeanimx[4] == 0.0f) {
                ClickGui.typeanimx[4] = typeanimto[4];
            }


            if (isHovered(rx - 4, ry, rx + font1.getStringWidth(c.name()) + 4, ry + blueheight, mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
                picker = null;
                ClickGui.lastcurType = c;
                if (c != ClickGui.curType) {
                    ClickGui.settingopen = false;
                    tempdy = 0;
                    dy = 0;
                }
                if (c == Category.Render) {
                    check = 2;
                } else if (c == Category.Misc) {
                    check = 3;
                } else if (c == Category.Cheat) {
                    check = 4;
                } else if (c == Category.Client) {
                    check = 5;
                }
                //check = 6;
                keydown = true;
                keydownX = (int) (mouseX - x);
                keydownY = (int) (mouseY - y);
            }

            rx += font1.getStringWidth(c.name()) + 8;
        }

        float[] speed = new float[8];
        if (ClickGui.typeanimx[0] > typeanimto[0] &&
                ClickGui.typeanimx[1] > typeanimto[1]) {
            speed[0] = 8;
            speed[1] = 12;
        } else {
            speed[0] = 12;
            speed[1] = 8;
        }
        ClickGui.typeanimx[0] = RenderUtils.toanim2(ClickGui.typeanimx[0], ClickGui.typeanimx[3], typeanimto[0], speed[0], 0.1f, 1f);
        ClickGui.typeanimx[1] = RenderUtils.toanim2(ClickGui.typeanimx[1], ClickGui.typeanimx[4], typeanimto[1], speed[1], 0.1f, 1f);
        //RenderUtils.drawRoundRect((int) (ClickGui.typeanimx[0] + x),  (int) (ry + blueheight - 2), (int) (ClickGui.typeanimx[1] + x), (int) (ry + blueheight), 1, CustomColor.getColor());
        RenderUtils.drawRoundRect((int) (ClickGui.typeanimx[0] + x), (int) (ry + blueheight - 2), (int) (ClickGui.typeanimx[1] + x), (int) (ry + blueheight), 1, new Color(255 - CustomColor.getColor().getRed(),255 - CustomColor.getColor().getGreen(),255 - CustomColor.getColor().getBlue(),128));


        rx = x + 5;
        float rxendto;
        if (!ClickGui.settingopen) {
            rxendto = -5;
        } else {
            rxendto = (float) (-0.5 * width - 2.5f);
        }

        if (rxendanim == 0) {
            rxendanim = rxendto;
        }

        if (!ClickGui.settingopen) {
            rxendanim = RenderUtils.toanim2(rxendanim, (float) (-0.5 * width - 2.5f), rxendto, 10, 0.1f, 1f);
        } else {
            rxendanim = RenderUtils.toanim2(rxendanim, -5, rxendto, 10, 0.1f, 1f);
        }


        float userxendanim = rxendanim + xend;

        ry += 16;

        if ((xend - 5) > (int) (userxendanim + 5)) {
            int round;
            round = Math.min(((xend - 5) - (int) (userxendanim + 5)) / 2, 4);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            RenderUtils.doGlScissor((int) (userxendanim + 5), (int) ry, xend - 5, yend - 5);

            RenderUtils.drawRoundRect((int) (userxendanim + 5), (int) ry, xend - 5, yend - 5, round, new Color(255, 255, 255));


            float thisry = ry + 8 + dy1;
            font.drawString(ClickGui.settingmodule.getName(), (int) (userxendanim + 13), (int) thisry, new Color(0, 0, 0).getRGB());

            //RenderUtils.drawRoundRect((int) (userxendanim + 143 - 8 - 32), (int) thisry - 5, (int) (userxendanim + 143 - 8), (int) (thisry + 7 ), 3, new Color(200, 200, 200));
            //font1.drawString("Bind", (int) (userxendanim + 143 - 8 - 32), thisry, new Color(64, 64, 64).getRGB());


            thisry += 10;
            RenderUtils.drawRect((int) (userxendanim + 10), (int) thisry, (int) (userxendanim + 143), (int) (thisry + 1), new Color(200, 200, 200).getRGB());
            thisry += 10;
            for (Setting s : ClickGui.settingmodule.getValues()) {

                if (s.isBoolean()) {
                    //RenderUtils.drawRect((int) (userxendanim + 18), (int) thisry - 2 - 4  , (int) (userxendanim + 145 -10), (int) thisry + 8 - 2 + 4,new Color(0,0,0).getRGB());
                    font1.drawString(s.getName(), (int) (userxendanim + 18), (int) thisry, new Color(64, 64, 64).getRGB());
                    RenderUtils.drawRoundRect((int) (userxendanim + 130 - 10), (int) thisry - 2, (int) (userxendanim + 145 - 10), (int) thisry + 8 - 2, 4, new Color(200, 200, 200));
                    if (isHovered((int) (userxendanim + 18), (int) thisry - 2 - 4, (int) (userxendanim + 145 - 10), (int) thisry + 8 - 2 + 4, mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
                        keydown = true;
                        keydownX = (int) (mouseX - x);
                        keydownY = (int) (mouseY - y);
                        s.setToggle(!s.isToggle());
                    }
                    int settingto;
                    int settingto1;
                    int redto;
                    int blueto;
                    settingto = s.isToggle() ? 145 - 5 - 10 : 130 + 5 - 10;
                    settingto1 = !s.isToggle() ? 145 - 5 - 10 : 130 + 5 - 10;
                    redto = s.isToggle() ? CustomColor.getColor().getRed() : 128;
                    blueto = s.isToggle() ? CustomColor.getColor().getBlue() : 128;

                    if (s.getSettingXY() == 0) {
                        s.setSettingXY(settingto);
                    }
                    if (s.getRed() == 0) {
                        s.setRed(redto);
                    }
                    if (s.getBlue() == 0) {
                        s.setBlue(blueto);
                    }
                    s.setSettingXY(RenderUtils.toanim2(s.getSettingXY(), settingto1, settingto, 4, 0.001f, 0.1f));
                    s.setRed(RenderUtils.toanim(s.getRed(), redto, 8, 0.1f));
                    s.setGreen(128);
                    s.setBlue(RenderUtils.toanim(s.getBlue(), blueto, 8, 0.1f));
                    RenderUtils.drawFilledCircle((int) (s.getSettingXY() + userxendanim), (int) (thisry - 2 + 4), 3, new Color((int) s.getRed(), (int) s.getGreen(), (int) s.getBlue()));
                }

                if (s.isValue()) {
                    font1.drawString(s.getName(), (int) (userxendanim + 18), (int) thisry, new Color(64, 64, 64).getRGB());
                    font1.drawString(String.valueOf(s.getCurrentValue()), (int) (userxendanim + 143 - 8 - font1.getStringWidth(String.valueOf(s.getCurrentValue()))), (int) thisry, new Color(64, 64, 64).getRGB());
                    thisry += 8;
                    RenderUtils.drawRoundRect((int) (userxendanim + 10 + 8), (int) thisry, (int) (userxendanim + 143 - 8), (int) (thisry + 4), 2, new Color(200, 200, 200));
                    double l = Math.min(1, Math.max(0, (s.getCurrentValue() - s.getMinValue()) / (s.getMaxValue() - s.getMinValue()))) * ((int) (userxendanim + 143 - 8) - (int) (userxendanim + 10 + 12));
                    //RenderUtils.drawFilledCircle((int) (userxendanim + 10 + 8 + 2 + l), (int) thisry + 2, 3, CustomColor.getColor());
                    RenderUtils.drawRoundRect((int) (userxendanim + 10 + 8), (int) thisry, (int) (userxendanim + 10 + 8 + 4 + l), (int) (thisry + 4), 2, CustomColor.getColor());
                    if (isHovered((int) (userxendanim + 10 + 8), (int) thisry, (int) (userxendanim + 143 - 8), (int) (thisry + 4), mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
                        check = 10;
                        check10setting = s.getName();
                        keydown = true;
                        keydownX = (int) (mouseX - x);
                        keydownY = (int) (mouseY - y);
                    }
                    if (check == 10 && check10setting.equals(s.getName())) {
                        if (mouseX > (userxendanim + 10 + 8 + 2 + l)) {
                            BigDecimal shit1 = new BigDecimal(Double.toString(s.getCurrentValue()));
                            BigDecimal shit2 = new BigDecimal(Double.toString(s.getIncValue()));
                            while (Math.abs(mouseX - (userxendanim + 10 + 8 + 2 + l)) > Math.abs(mouseX - (userxendanim + 10 + 8 + 2 + (Math.min(1, Math.max(0, (Math.min(shit1.add(shit2).doubleValue(), s.getMaxValue()) - s.getMinValue()) / (s.getMaxValue() - s.getMinValue()))) * ((userxendanim + 143 - 8) - (userxendanim + 10 + 12))))) || s.getCurrentValue() < s.getMinValue()) {
                                s.setCurrentValue(Math.min(shit1.add(shit2).doubleValue(), s.getMaxValue()));
                                shit1 = new BigDecimal(Double.toString(s.getCurrentValue()));
                                shit2 = new BigDecimal(Double.toString(s.getIncValue()));
                                l = Math.min(1, Math.max(0, (s.getCurrentValue() - s.getMinValue()) / (s.getMaxValue() - s.getMinValue()))) * ((int) (userxendanim + 143 - 8) - (int) (userxendanim + 10 + 12));
                            }
                        }
                        if (mouseX < (userxendanim + 10 + 8 + 2 + l)) {
                            BigDecimal shit1 = new BigDecimal(Double.toString(s.getCurrentValue()));
                            BigDecimal shit2 = new BigDecimal(Double.toString(s.getIncValue()));
                            while (Math.abs(mouseX - (userxendanim + 10 + 8 + 2 + l)) > Math.abs(mouseX - (userxendanim + 10 + 8 + 2 + (Math.min(1, Math.max(0, (Math.min(shit1.subtract(shit2).doubleValue(), s.getMaxValue()) - s.getMinValue()) / (s.getMaxValue() - s.getMinValue()))) * ((userxendanim + 143 - 8) - (userxendanim + 10 + 12))))) || s.getCurrentValue() > s.getMaxValue()) {
                                s.setCurrentValue(Math.max(shit1.subtract(shit2).doubleValue(), s.getMinValue()));
                                shit1 = new BigDecimal(Double.toString(s.getCurrentValue()));
                                shit2 = new BigDecimal(Double.toString(s.getIncValue()));
                                l = Math.min(1, Math.max(0, (s.getCurrentValue() - s.getMinValue()) / (s.getMaxValue() - s.getMinValue()))) * ((int) (userxendanim + 143 - 8) - (int) (userxendanim + 10 + 12));
                            }
                        }
                    }
                }
                if (s.isColor()) {
                    //RenderUtils.drawRect((int) (userxendanim + 18), (int) thisry - 2 - 4  , (int) (userxendanim + 145 -10), (int) thisry + 8 - 2 + 4,new Color(0,0,0).getRGB());
                    font1.drawString(s.getName(), (int) (userxendanim + 18), (int) thisry, new Color(64, 64, 64).getRGB());
                    bloomFramebuffer = GaussianBlur.createFrameBuffer(bloomFramebuffer);
                    bloomFramebuffer.framebufferClear();
                    bloomFramebuffer.bindFramebuffer(true);
                    RenderUtils.drawRoundRect((int) (userxendanim + 130 - 10), (int) thisry - 2, (int) (userxendanim + 145 - 10), (int) thisry + 8 - 2, 4, s.getColor());
                    BloomUtil.renderBlur(bloomFramebuffer.framebufferTexture, 10, 2);

                    RenderUtils.drawRoundRect((int) (userxendanim + 130 - 10), (int) thisry - 2, (int) (userxendanim + 145 - 10), (int) thisry + 8 - 2, 4, s.getColor());

                    if (isHovered((int) (userxendanim + 18), (int) thisry - 2 - 4, (int) (userxendanim + 145 - 10), (int) thisry + 8 - 2 + 4, mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
                        keydown = true;
                        keydownX = (int) (mouseX - x);
                        keydownY = (int) (mouseY - y);
                        picker = new Picker(s,mouseX + 10,mouseY + 10);
                    }
                }

                if (s.isMode()) {
                    for (int i = 0; i < s.getModes().size(); i++) {
                        if (s.getModes().get(i).equals(s.getCurrentMode())) {
                            Collections.swap(s.getModes(), i, 0);
                        }
                    }
                    font1.drawString(s.getName(), (int) (userxendanim + 18), (int) thisry, new Color(64, 64, 64).getRGB());
                    RenderUtils.drawRoundRect((int) (userxendanim + 143 - 8 - 32), (int) thisry - 5, (int) (userxendanim + 143 - 8), (int) (thisry + 7 + s.getSettingXY()), 3, new Color(200, 200, 200));
                    //font1.drawString(s.getCurrentMode(), (int) (((userxendanim + 143 - 8 - 32) + (userxendanim + 143 - 8) - font1.getStringWidth(s.getCurrentMode())) / 2), (int) thisry, new Color(64, 64, 64).getRGB());
                    if (isHovered((int) (userxendanim + 143 - 8 - 32), (int) thisry - 5, (int) (userxendanim + 143 - 8), (int) (thisry + 7), mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
                        keydown = true;
                        keydownX = (int) (mouseX - x);
                        keydownY = (int) (mouseY - y);
                        s.setOpen(!s.isOpen());
                    }
                    if (isHovered((int) (userxendanim + 143 - 8 - 32), (int) thisry - 5, (int) (userxendanim + 143 - 8), (int) (thisry + 7 + s.getSettingXY()), mouseX, mouseY) && Mouse.isButtonDown(1) && !keydown1) {
                        keydown1 = true;
                        keydownX = (int) (mouseX - x);
                        keydownY = (int) (mouseY - y);
                        s.setOpen(!s.isOpen());
                    }

                    for (int i = 0; i < s.getModes().size(); i++) {
                        RenderUtils.drawRect((int) (userxendanim + 143 - 8 - 32 + 3), (int) (thisry - 5 + 12 + i * 12), (int) (userxendanim + 143 - 8 - 3), (int) (thisry - 5 + 12 + 1 + i * 12), new Color(150, 150, 150).getRGB());
                        font1.drawString(s.getModes().get(i), (int) (((userxendanim + 143 - 8 - 32) + (userxendanim + 143 - 8) - font1.getStringWidth(s.getModes().get(i))) / 2), (int) (thisry + i * 12), new Color(64, 64, 64).getRGB());
                        //RenderUtils.drawRect((int) (userxendanim + 143 - 8 - 32), (int) (thisry - 5 + i * 12), (int) (userxendanim + 143 - 8), (int) (thisry - 5 + 12 + i * 12), new Color(0, 0, 0,64).getRGB());
                        if (isHovered((int) (userxendanim + 143 - 8 - 32), (int) (thisry - 5 + i * 12), (int) (userxendanim + 143 - 8), (int) (thisry - 5 + 12 + i * 12), mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown && s.isOpen()) {
                            keydown = true;
                            keydownX = (int) (mouseX - x);
                            keydownY = (int) (mouseY - y);
                            s.setCurrentMode(s.getModes().get(i));
                            s.setOpen(false);
                        }
                    }

                    RenderUtils.drawRect((int) (userxendanim + 143 - 8 - 32), (int) (thisry + 7 + s.getSettingXY()), (int) (userxendanim + 143 - 8), (int) (y + height), new Color(255, 255, 255).getRGB());

                    int openyto;
                    int openyto1;
                    if (s.isOpen()) {
                        openyto = (s.getModes().size() - 1) * 12;
                        openyto1 = 0;
                    } else {
                        openyto = 0;
                        openyto1 = (s.getModes().size() - 1) * 12;
                    }
                    float tempr = RenderUtils.toanim2(s.getSettingXY(), openyto1, openyto, 8, 0.1f, 0.5f);
                    s.setSettingXY(tempr);
                    thisry += tempr;
                }

                thisry += 16;
            }

            GL11.glDisable(GL11.GL_SCISSOR_TEST);

            if (System.nanoTime() - time > 30000000f) { //0.03s
                if (tempy1 > 0) {
                    tempy1 = 0;
                }
            }

            if (isHovered((int) (userxendanim + 5), (int) ry, xend - 5, yend - 5, mouseX, mouseY)) {

                int dwheel = Mouse.getDWheel();

                if (dwheel != 0) {
                    time = System.nanoTime();
                }

                if (dwheel < 0) {
                    tempy1 -= 16;
                }
                if (dwheel > 0) {
                    tempy1 += 16;
                }
            }

            dy1 = RenderUtils.toanim(dy1, tempy1, 8, 0.1f);
        }

        ry += dy;
        int itemp = height - 16;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        RenderUtils.doGlScissor((int) rx, (int) y + 12 + 4, (int) userxendanim, (int) (y + height));

        for (Module m : ModuleManager.getModules()) {
            float coloranimto;
            if (m.isToggle()) {
                coloranimto = 255;
            } else {
                coloranimto = 175;
            }

            m.setColoranim(toanim(m.getColoranim(), coloranimto, 16, 0.1f));
            float coloranim = m.getColoranim();
            Color canim = new Color((int) coloranim, (int) coloranim, (int) coloranim);

            float fontcoloranim = m.getColoranim();
            Color fontcanim = new Color(0, 0, 0, (int) fontcoloranim);

            if (m.getModuletype() == ClickGui.curType) {

                itemp -= 20;

                RenderUtils.drawRoundRect((int) rx, (int) ry, (int) userxendanim, (int) (ry + 16), 4, canim);

                float fontytemp = ry + (20 - font.getStringHeight(m.getName())) / 2 - 1;
                float fontxtemp = rx + 5;
                font.drawString(m.getName(), fontxtemp, fontytemp, fontcanim.getRGB());

                if (isHovered((int) rx, (int) y + 12, (int) userxendanim, (int) (y + height), mouseX, mouseY)) {

                    if (isHovered((int) rx, (int) ry, (int) userxendanim, (int) (ry + 16), mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
                        check = 7;
                        keydown = true;
                        keydownX = (int) (mouseX - x);
                        keydownY = (int) (mouseY - y);
                        togglemodule = m;
                        picker = null;
                    }
                    if (isHovered((int) rx, (int) ry, (int) userxendanim, (int) (ry + 16), mouseX, mouseY) && Mouse.isButtonDown(1) && !keydown1) {
                        picker = null;
                        check = 8;
                        keydown1 = true;
                        keydownX = (int) (mouseX - x);
                        keydownY = (int) (mouseY - y);
                        if (!ClickGui.settingopen) {
                            tempy1 =0;
                            dy1 = 0;
                            ClickGui.settingmodule = m;
                            ClickGui.settingopen = true;
                        } else {
                            if (ClickGui.settingmodule == m) {
                                ClickGui.settingopen = false;
                            } else {
                                ClickGui.settingmodule = m;
                                tempy1 =0;
                                dy1 = 0;
                            }
                        }
                    }

                }

                ry += 20;
            }
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        if (System.nanoTime() - time > 30000000f) { //0.03s
            if (tempdy > 0) {
                tempdy = 0;
            }

            if (tempdy < itemp && itemp < 0) {
                tempdy = itemp;
            }

            if (itemp > 0) {
                tempdy = 0;
            }
        }

        if (isHovered((int) rx, (int) y + 12, (int) userxendanim, (int) (y + height), mouseX, mouseY)) {

            int dwheel = Mouse.getDWheel();

            if (dwheel != 0) {
                time = System.nanoTime();
            }

            if (dwheel < 0) {
                tempdy -= 16;
            }
            if (dwheel > 0) {
                tempdy += 16;
            }
        }

        dy = RenderUtils.toanim(dy, tempdy, 8, 0.1f);

        int customstartxto = windowX - 50;
        int customstarty = windowY - 25;
        int customwidth = 40;
        int customheight = 15;
        String customtext = "Custom";

        customend = true;
        if (customend) {
            customx = customstartxto;
        } else {
            customx = toanim(customx, customstartxto, 8, 1f);
        }

        if (customx == customstartxto) {
            customend = true;
        }
        int customstartx = (int) customx;

        int fontstartx = (customwidth - font.getStringWidth(customtext)) / 2 + customstartx;
        int fontstarty = (customheight - font.getStringHeight(customtext)) / 2 + customstarty + 1;

        RenderUtils.drawRoundRect(customstartx, customstarty, customstartx + customwidth, customstarty + customheight, 4, CustomColor.getColor());
        font.drawString(customtext, fontstartx, fontstarty, new Color(255, 255, 255).getRGB());
        if (isHovered(customstartx, customstarty, customstartx + customwidth, customstarty + customheight, mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
            check = 9;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        check(mouseX, mouseY);

        if (toclose && alpha == alphato && anithisryto == animgety) {
            toclose = false;
        }

        if(picker !=null)
            picker.draw(mouseX,mouseY);
    }




    public float toanim(float now, float end, float multiplier, float min) {
        return RenderUtils.toanim(now, end, multiplier, min);
    }

    public void check(int mouseX, int mouseY) {

        if (check == 0) {
            float cgx = ClickGui.x;
            float cgy = ClickGui.y;
            float end;
            float mult = 8.0f;
            float min = 1f;
            if (windowX > width && cgx + width > windowX) {
                end = windowX - width;
                ClickGui.x = toanim(cgx, end, mult, min);
            }

            if (windowY > height && cgy + height > windowY) {
                end = windowY - height;
                ClickGui.y = toanim(cgy, end, mult, min);
            }

            if (windowX > width && cgx < 0) {
                end = 0;
                ClickGui.x = toanim(cgx, end, mult, min);
            }

            if (windowY > height && cgy < 0) {
                end = 0;
                ClickGui.y = toanim(cgy, end, mult, min);
            }
        }

        if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
            check = 0;
            keydown = false;
            keydown1 = false;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (isHovered(x, y, xend, y + 12, mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
            check = 1;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (Mouse.isButtonDown(0) && !keydown && check == 0) {
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (check == 9) {
            this.mc.displayGuiScreen(new Custom());
        }

        if (check == 1) {
            ClickGui.x = mouseX - keydownX;
            ClickGui.y = mouseY - keydownY;
        }

        if (check == 2) {
            ClickGui.curType = Category.Render;
        } else if (check == 3) {
            ClickGui.curType = Category.Misc;
        } else if (check == 4) {
            ClickGui.curType = Category.Cheat;
        } else if (check == 5) {
            ClickGui.curType = Category.Client;
        } /*else if (check == 6) {

        }*/

        if (check == 7) {

            togglemodule.toggle();
            check = 0;
        }

        if (check == 8) {
            check = 0;
        }

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (picker != null)
            picker.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (picker != null)
            picker.mouseReleased(mouseX, mouseY, state);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
    private static Framebuffer bloomFramebuffer = new Framebuffer(1, 1, false);

    public static class Picker {
        public int x;
        public int y;

        public Setting colorValue;

        private float hue;
        private float saturation;
        private float brightness;
        private float alpha;
        private boolean colorSelectorDragging;
        private boolean hueSelectorDragging;
        private boolean alphaSelectorDragging;

        public Picker (Setting s, int x, int y) {
            this.x = x;
            this.y = y;
            this.colorValue = s;
            updateValue(s.getColor().getRGB());
        }


        public void draw (int mouseX, int mouseY) {
            float x2 = x;
            float y2 = y;

            float width = 11;
            float height = 5;

            //绘制调色板SKID
            int black = getColor(0);
             int guiAlpha = 255;
            int color = colorValue.getColor().getRGB();
            int colorAlpha = color >> 24 & 0xFF;
            int minAlpha = Math.min(guiAlpha, colorAlpha);


            int newColor = new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, minAlpha).getRGB();
            //drawGradientRect(x2, y2, x2 + width, y2 + height, newColor, darker(newColor, 0.6f));

            float hueSelectorY;
            float hueSliderYDif;
            float alphaSliderBottom;
            float hueSliderRight;
            GL11.glTranslated(0.0, 0.0, 3.0);
            float expandedX = this.getExpandedX();
            float expandedY = this.getExpandedY();
            float expandedWidth = this.getExpandedWidth();
            float expandedHeight = this.getExpandedHeight();
            bloomFramebuffer = GaussianBlur.createFrameBuffer(bloomFramebuffer);
            bloomFramebuffer.framebufferClear();
            bloomFramebuffer.bindFramebuffer(true);
            RenderUtils.drawRoundRect((int) expandedX, (int) expandedY, (int) (expandedX + expandedWidth), (int) (expandedY + expandedHeight),2, Color.WHITE);bloomFramebuffer.unbindFramebuffer();
            BloomUtil.renderBlur(bloomFramebuffer.framebufferTexture, 10, 2);
            RenderUtils.drawRoundRect((int) expandedX, (int) expandedY, (int) (expandedX + expandedWidth), (int) (expandedY + expandedHeight),2, Color.WHITE);
            float colorPickerSize = expandedWidth - 9.0f - 8.0f;
            float colorPickerLeft = expandedX + 3.0f;
            float colorPickerTop = expandedY + 3.0f;
            float colorPickerRight = colorPickerLeft + colorPickerSize;
            float colorPickerBottom = colorPickerTop + colorPickerSize;
            int selectorWhiteOverlayColor = new Color(255, 255, 255, Math.min(guiAlpha, 180)).getRGB();

            if ((float) mouseX <= colorPickerLeft || (float) mouseY <= colorPickerTop || (float) mouseX >= colorPickerRight || (float) mouseY >= colorPickerBottom) {
                this.colorSelectorDragging = false;
            }


            drawRect(colorPickerLeft - 0.5f, colorPickerTop - 0.5f, colorPickerRight + 0.5f, colorPickerBottom + 0.5f, getColor(0));


            this.drawColorPickerRect(colorPickerLeft, colorPickerTop, colorPickerRight, colorPickerBottom);
            float hueSliderLeft = this.saturation * (colorPickerRight - colorPickerLeft);
            float alphaSliderTop = (1.0f - this.brightness) * (colorPickerBottom - colorPickerTop);
            if (this.colorSelectorDragging) {
                hueSliderRight = colorPickerRight - colorPickerLeft;
                alphaSliderBottom = (float) mouseX - colorPickerLeft;
                this.saturation = alphaSliderBottom / hueSliderRight;
                hueSliderLeft = alphaSliderBottom;
                hueSliderYDif = colorPickerBottom - colorPickerTop;
                hueSelectorY = (float) mouseY - colorPickerTop;
                this.brightness = 1.0f - hueSelectorY / hueSliderYDif;
                alphaSliderTop = hueSelectorY;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
            }
            hueSliderRight = colorPickerLeft + hueSliderLeft - 0.5f;
            alphaSliderBottom = colorPickerTop + alphaSliderTop - 0.5f;
            hueSliderYDif = colorPickerLeft + hueSliderLeft + 0.5f;
            hueSelectorY = colorPickerTop + alphaSliderTop + 0.5f;
            drawRect(hueSliderRight - 0.5f, alphaSliderBottom - 0.5f, hueSliderRight, hueSelectorY + 0.5f, black);
            drawRect(hueSliderYDif, alphaSliderBottom - 0.5f, hueSliderYDif + 0.5f, hueSelectorY + 0.5f, black);
            drawRect(hueSliderRight, alphaSliderBottom - 0.5f, hueSliderYDif, alphaSliderBottom, black);
            drawRect(hueSliderRight, hueSelectorY, hueSliderYDif, hueSelectorY + 0.5f, black);
            drawRect(hueSliderRight, alphaSliderBottom, hueSliderYDif, hueSelectorY, selectorWhiteOverlayColor);
            hueSliderLeft = colorPickerRight + 3.0f;
            hueSliderRight = hueSliderLeft + 8.0f;
            if ((float) mouseX <= hueSliderLeft || (float) mouseY <= colorPickerTop || (float) mouseX >= hueSliderRight || (float) mouseY >= colorPickerBottom) {
                this.hueSelectorDragging = false;
            }
            hueSliderYDif = colorPickerBottom - colorPickerTop;
            hueSelectorY = (1.0f - this.hue) * hueSliderYDif;
            if (this.hueSelectorDragging) {
                float inc = (float) mouseY - colorPickerTop;
                this.hue = 1.0f - inc / hueSliderYDif;
                hueSelectorY = inc;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
            }
            drawRect(hueSliderLeft - 0.5f, colorPickerTop - 0.5f, hueSliderRight + 0.5f, colorPickerBottom + 0.5f, black);
            float hsHeight = colorPickerBottom - colorPickerTop;
            float alphaSelectorX = hsHeight / 5.0f;
            float asLeft = colorPickerTop;
            int i2 = 0;
            while ((float) i2 < 5.0f) {
                boolean last = (float) i2 == 4.0f;
                drawGradientRect(hueSliderLeft, asLeft, hueSliderRight, asLeft + alphaSelectorX, getColor(Color.HSBtoRGB(1.0f - 0.2f * (float) i2, 1.0f, 1.0f)), getColor(Color.HSBtoRGB(1.0f - 0.2f * (float) (i2 + 1), 1.0f, 1.0f)));
                if (! last) {
                    asLeft += alphaSelectorX;
                }
                ++ i2;
            }
            float hsTop = colorPickerTop + hueSelectorY - 0.5f;
            float asRight = colorPickerTop + hueSelectorY + 0.5f;
            drawRect(hueSliderLeft - 0.5f, hsTop - 0.5f, hueSliderLeft, asRight + 0.5f, black);
            drawRect(hueSliderRight, hsTop - 0.5f, hueSliderRight + 0.5f, asRight + 0.5f, black);
            drawRect(hueSliderLeft, hsTop - 0.5f, hueSliderRight, hsTop, black);
            drawRect(hueSliderLeft, asRight, hueSliderRight, asRight + 0.5f, black);
            drawRect(hueSliderLeft, hsTop, hueSliderRight, asRight, selectorWhiteOverlayColor);
            alphaSliderTop = colorPickerBottom + 3.0f;
            alphaSliderBottom = alphaSliderTop + 8.0f;
            if ((float) mouseX <= colorPickerLeft || (float) mouseY <= alphaSliderTop || (float) mouseX >= colorPickerRight || (float) mouseY >= alphaSliderBottom) {
                this.alphaSelectorDragging = false;
            }
            int z2 = Color.HSBtoRGB(this.hue, this.saturation, this.brightness);
            int r2 = z2 >> 16 & 0xFF;
            int g2 = z2 >> 8 & 0xFF;
            int b2 = z2 & 0xFF;
            hsHeight = colorPickerRight - colorPickerLeft;
            alphaSelectorX = this.alpha * hsHeight;
            if (this.alphaSelectorDragging) {
                asLeft = (float) mouseX - colorPickerLeft;
                this.alpha = asLeft / hsHeight;
                alphaSelectorX = asLeft;
                this.updateColor(new Color(r2, g2, b2, (int) (this.alpha * 255.0f)).getRGB(), true);
            }
            drawRect(colorPickerLeft - 0.5f, alphaSliderTop - 0.5f, colorPickerRight + 0.5f, alphaSliderBottom + 0.5f, black);
            drawGradientRect(colorPickerLeft, alphaSliderTop, colorPickerRight, alphaSliderBottom, true, new Color(r2, g2, b2, 0).getRGB(), new Color(r2, g2, b2, Math.min(guiAlpha, 255)).getRGB());
            asLeft = colorPickerLeft + alphaSelectorX - 0.5f;
            asRight = colorPickerLeft + alphaSelectorX + 0.5f;
            drawRect(asLeft - 0.5f, alphaSliderTop, asRight + 0.5f, alphaSliderBottom, black);
            drawRect(asLeft, alphaSliderTop, asRight, alphaSliderBottom, selectorWhiteOverlayColor);
            GL11.glTranslated(0.0, 0.0, - 3.0);

        }


        public void drawRect(float x1, float y1, float x2, float y2, int color) {
            GL11.glPushMatrix();
            GlStateManager.enableBlend();
            glEnable(GL_BLEND);
            GL11.glDisable(GL_TEXTURE_2D);
            GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_LINE_SMOOTH);
            GL11.glPushMatrix();
            color(color);
            GL11.glBegin(7);
            GL11.glVertex2d(x2, y1);
            GL11.glVertex2d(x1, y1);
            GL11.glVertex2d(x1, y2);
            GL11.glVertex2d(x2, y2);
            GL11.glEnd();
            GL11.glPopMatrix();
            glEnable(GL_TEXTURE_2D);
            GL11.glDisable(GL_BLEND);
            GL11.glDisable(GL_LINE_SMOOTH);
            GlStateManager.disableBlend();
            GL11.glPopMatrix();
        }


        public static int darker (int color, float factor) {
            int r = (int) ((float) (color >> 16 & 255) * factor);
            int g = (int) ((float) (color >> 8 & 255) * factor);
            int b = (int) ((float) (color & 255) * factor);
            int a = color >> 24 & 255;
            return (r & 255) << 16 | (g & 255) << 8 | b & 255 | (a & 255) << 24;
        }

        public void mouseClicked (int mouseX, int mouseY, int mouseButton) {
            if (mouseButton == 0) {
                float expandedX = this.getExpandedX();
                float expandedY = this.getExpandedY();
                float expandedWidth = this.getExpandedWidth();
                float expandedHeight = this.getExpandedHeight();
                float colorPickerSize = expandedWidth - 9.0f - 8.0f;
                float colorPickerLeft = expandedX + 3.0f;
                float colorPickerTop = expandedY + 3.0f;
                float colorPickerRight = colorPickerLeft + colorPickerSize;
                float colorPickerBottom = colorPickerTop + colorPickerSize;
                float alphaSliderTop = colorPickerBottom + 3.0f;
                float alphaSliderBottom = alphaSliderTop + 8.0f;
                float hueSliderLeft = colorPickerRight + 3.0f;
                float hueSliderRight = hueSliderLeft + 8.0f;
                this.colorSelectorDragging = ! this.colorSelectorDragging && (float) mouseX > colorPickerLeft && (float) mouseY > colorPickerTop && (float) mouseX < colorPickerRight && (float) mouseY < colorPickerBottom;
                this.alphaSelectorDragging = ! this.alphaSelectorDragging && (float) mouseX > colorPickerLeft && (float) mouseY > alphaSliderTop && (float) mouseX < colorPickerRight && (float) mouseY < alphaSliderBottom;
                this.hueSelectorDragging = ! this.hueSelectorDragging && (float) mouseX > hueSliderLeft && (float) mouseY > colorPickerTop && (float) mouseX < hueSliderRight && (float) mouseY < colorPickerBottom;
            }
        }

        public void mouseReleased (int mouseX, int mouseY, int state) {
            if (this.hueSelectorDragging) {
                this.hueSelectorDragging = false;
            } else if (this.colorSelectorDragging) {
                this.colorSelectorDragging = false;
            } else if (this.alphaSelectorDragging) {
                this.alphaSelectorDragging = false;
            }
        }

        public void updateColor (int hex, boolean hasAlpha) {
            if (hasAlpha) {
                colorValue.setColor(new Color(hex));
            } else {
                colorValue.setColor(new Color(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, (int) (this.alpha * 255.0f)));
            }
        }

        private void drawColorPickerRect (float left, float top, float right, float bottom) {
            int hueBasedColor = getColor(Color.HSBtoRGB(this.hue, 1.0F, 1.0F));
            drawGradientRect(left, top, right, bottom, true, getColor(16777215), hueBasedColor);
            drawGradientRect(left, top, right, bottom, 0, getColor(0));
        }

        public static void drawGradientRect (float left, float top, float right, float bottom, int startColor, int endColor) {
            float f = (float) (startColor >> 24 & 255) / 255.0F;
            float f1 = (float) (startColor >> 16 & 255) / 255.0F;
            float f2 = (float) (startColor >> 8 & 255) / 255.0F;
            float f3 = (float) (startColor & 255) / 255.0F;
            float f4 = (float) (endColor >> 24 & 255) / 255.0F;
            float f5 = (float) (endColor >> 16 & 255) / 255.0F;
            float f6 = (float) (endColor >> 8 & 255) / 255.0F;
            float f7 = (float) (endColor & 255) / 255.0F;
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.shadeModel(7425);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(right, top, 0).color(f1, f2, f3, f).endVertex();
            worldrenderer.pos(left, top, 0).color(f1, f2, f3, f).endVertex();
            worldrenderer.pos(left, bottom, 0).color(f5, f6, f7, f4).endVertex();
            worldrenderer.pos(right, bottom, 0).color(f5, f6, f7, f4).endVertex();
            tessellator.draw();
            GlStateManager.shadeModel(7424);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
        }

        public static void drawGradientRect (double left, double top, double right, double bottom, boolean sideways, int startColor, int endColor) {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glBegin(GL11.GL_QUADS);
            color(startColor);
            if (sideways) {
                GL11.glVertex2d(left, top);
                GL11.glVertex2d(left, bottom);
                color(endColor);
                GL11.glVertex2d(right, bottom);
                GL11.glVertex2d(right, top);
            } else {
                GL11.glVertex2d(left, top);
                color(endColor);
                GL11.glVertex2d(left, bottom);
                GL11.glVertex2d(right, bottom);
                color(startColor);
                GL11.glVertex2d(right, top);
            }
            GL11.glEnd();
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        public static int getColor (int color) {
            int r = color >> 16 & 0xFF;
            int g = color >> 8 & 0xFF;
            int b = color & 0xFF;
            int a = 255;
            return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
        }


        public static void color (int color) {
            float f = (float) (color >> 24 & 255) / 255.0f;
            float f1 = (float) (color >> 16 & 255) / 255.0f;
            float f2 = (float) (color >> 8 & 255) / 255.0f;
            float f3 = (float) (color & 255) / 255.0f;
            GL11.glColor4f(f1, f2, f3, f);
        }


        public void updateValue (int value) {
            float[] hsb = this.getHSBFromColor(value);
            this.hue = hsb[ 0 ];
            this.saturation = hsb[ 1 ];
            this.brightness = hsb[ 2 ];
            this.alpha = (float) (value >> 24 & 255) / 255.0f;
        }

        private float[] getHSBFromColor (int hex) {
            int r2 = hex >> 16 & 0xFF;
            int g2 = hex >> 8 & 0xFF;
            int b2 = hex & 0xFF;
            return Color.RGBtoHSB(r2, g2, b2, null);
        }

        public float getExpandedX () {
            return x;
        }

        public float getExpandedY () {
            return y;
        }

        public float getExpandedWidth () {
            float right = x + 27.5f + 125 + 11;
            return right - this.getExpandedX();
        }

        public float getExpandedHeight () {
            return getExpandedWidth();
        }
    }
}
