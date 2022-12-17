package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.Dot.Client;
import xyz.Dot.module.Category;
import xyz.Dot.module.Client.ClickGui;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;

public class ClickUI extends GuiScreen {
    CFontRenderer font = FontLoaders.normalfont16; // 字体
    CFontRenderer font1 = FontLoaders.normalfont12; // 字体
    int windowX, windowY; // 窗口大小
    int width, height; // ClickGui大小
    float x, y = RenderUtils.height(); // ClickGui位置
    int xend, yend; // ClickGui位置终
    boolean keydown = false, keydown1 = false; // 是否按下左键、右键
    int check = 0; // (0 null) (1 X,Y) (2combat 3movemnt 4player 5render 6保留 curtype) (7 moduletoggle) (8 module setting) (9 custom)
    int keydownX, keydownY; // 按下左键时的X Y
    float beterspeedinfps; // 动画帧率修补量
    boolean animyaninend = false; //进入动画Y轴是否结束
    float alpha = 5; //背景初始亮度
    public static boolean toclose = false; //关闭动画所需参数 还有bug暂时没做好先留个接口
    float[] typeanimto = new float[8]; // 类别动画目标位置
    Module togglemodule; // 开关module暂存
    float rxendanim = 0;
    float customx = RenderUtils.width();
    boolean customend = false;
    static float tempdy, dy;

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        //toclose = true;
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

        float animyto;
        if (!toclose) {
            animyto = ClickGui.y;
        } else {
            animyto = RenderUtils.height();
        }

        x = ClickGui.x;
        //x = toanim(x, ClickGui.x, 2, 1f);
        float animgety = 0;
        if (!animyaninend || toclose) {
            animgety = toanim(y, animyto, 8, 1f);
            if (animyto == animgety) {
                animyaninend = true;
            } else {
                y = animgety;
            }
        } else {
            y = animyto;
            //y = toanim(y, ClickGui.y, 2, 1f);
        }

        float rx = x;
        float ry = y;
        float blueheight = 12;
        xend = (int) (rx + width);
        yend = (int) (ry + height);

        RenderUtils.drawRect((int) rx, (int) ry, xend, (int) (ry + blueheight), new Color(64, 128, 255, 200).getRGB());
        RenderUtils.drawRect((int) rx, (int) (ry + blueheight), xend, yend, new Color(255, 255, 255, 200).getRGB());

        String thisnametext = "ClickGui";
        font.drawString(thisnametext, rx + 5, ry + 4, new Color(255, 255, 255).getRGB());

        rx += font1.getStringWidth(thisnametext) + 32;

        float fontrendery = y + (blueheight - font1.getStringHeight(ClickGui.curType.name())) / 2 + 1;
        for (Category c : Category.values()) {
            //RenderUtils.drawRect((int) rx, (int) ry, (int) (rx + 0.3f * width), (int) (ry + 0.0625f * height), new Color(255, 255, 255).getRGB());
            font1.drawString(c.name(), rx, fontrendery, new Color(255, 255, 255).getRGB());

            if (c == ClickGui.curType) {
                typeanimto[0] = rx - 4 - x;
                typeanimto[1] = rx + font1.getStringWidth(c.name()) + 4 - x;
            }

            if (ClickGui.typeanimx[0] == 0.0) {
                ClickGui.typeanimx[0] = typeanimto[0];
            }
            if (ClickGui.typeanimx[1] == 0.0f) {
                ClickGui.typeanimx[1] = typeanimto[1];
            }

            if (isHovered(rx - 4, ry, rx + font1.getStringWidth(c.name()) + 4, ry + blueheight, mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
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
        ClickGui.typeanimx[0] = toanim(ClickGui.typeanimx[0], typeanimto[0], speed[0], 0.1f);
        ClickGui.typeanimx[1] = toanim(ClickGui.typeanimx[1], typeanimto[1], speed[1], 0.1f);
        RenderUtils.drawRect((int) (ClickGui.typeanimx[0] + x), (int) ry, (int) (ClickGui.typeanimx[1] + x), (int) (ry + blueheight), new Color(255, 255, 255, 128).getRGB());

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

        rxendanim = toanim(rxendanim, rxendto, 12, 0.1f);

        float userxendanim = rxendanim + xend;

        ry += 16;

        if ((xend - 5) > (int) (userxendanim + 5)) {
            int round;
            if (((xend - 5) - (int) (userxendanim + 5)) > 1) {
                round = 1;
            } else {
                round = 0;
            }

            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            RenderUtils.doGlScissor((int) (userxendanim + 5), (int) ry, xend - 5, yend - 5);

            RenderUtils.drawRoundRect((int) (userxendanim + 5), (int) ry, xend - 5, yend - 5, round, new Color(255, 255, 255));
            font.drawString(ClickGui.settingmodule.getName(), (int) (userxendanim + 13), (int) (ry + 8), new Color(0, 0, 0).getRGB());

            for (Setting s : ClickGui.settingmodule.getValues()) {


            }

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        ry += dy;
        int itemp = height - 16;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        RenderUtils.doGlScissor((int) rx, (int) y + 12, (int) userxendanim, (int) (y + height));

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

            if (m.getModuletype() != ClickGui.curType) {

                itemp -= 20;

                RenderUtils.drawRoundRect((int) rx, (int) ry, (int) userxendanim, (int) (ry + 16), 1, canim);

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
                    }
                    if (isHovered((int) rx, (int) ry, (int) userxendanim, (int) (ry + 16), mouseX, mouseY) && Mouse.isButtonDown(1) && !keydown1) {
                        check = 8;
                        keydown1 = true;
                        keydownX = (int) (mouseX - x);
                        keydownY = (int) (mouseY - y);
                        if (!ClickGui.settingopen) {
                            ClickGui.settingmodule = m;
                            ClickGui.settingopen = true;
                        } else {
                            if (ClickGui.settingmodule == m) {
                                ClickGui.settingopen = false;
                            } else {
                                ClickGui.settingmodule = m;
                            }
                        }
                    }

                }

                ry += 20;
            }
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        if (isHovered((int) rx, (int) y + 12, (int) userxendanim, (int) (y + height), mouseX, mouseY)) {

            if (tempdy > 0) {
                tempdy = 0;
            }

            if (tempdy < itemp && itemp < 0) {
                tempdy = itemp;
            }

            if (itemp > 0) {
                tempdy = 0;
            }

            int dwheel = Mouse.getDWheel();
            if (dwheel < 0) {
                tempdy -= 16;
            }
            if (dwheel > 0) {
                tempdy += 16;
            }

            dy = RenderUtils.toanim(dy, tempdy, 8, 0.1f);
        }

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

        RenderUtils.drawRoundRect(customstartx, customstarty, customstartx + customwidth, customstarty + customheight, 1, new Color(64, 128, 255));
        font.drawString(customtext, fontstartx, fontstarty, new Color(255, 255, 255).getRGB());
        if (isHovered(customstartx, customstarty, customstartx + customwidth, customstarty + customheight, mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
            check = 9;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        check(mouseX, mouseY);

        if (toclose && alpha == alphato && animyto == animgety) {
            toclose = false;
        }

    }

    public float toanim(float now, float end, float multiplier, float min) {
        return RenderUtils.toanim(now, end, multiplier, min);
    }

    public void check(int mouseX, int mouseY) {

        if (!keydown) {
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
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
