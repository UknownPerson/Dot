package xyz.Dot.ui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.Dot.event.EventHandler;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.module.Render.ClickGui;
import xyz.Dot.utils.Helper;
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
    boolean keydown = false; // 是否按下左键
    int check = 0; // (0 null) (1 X,Y) (2combat 3movemnt 4player 5render 6world curtype)
    int keydownX, keydownY; // 按下左键时的X Y
    float beterspeedinfps; // 动画帧率修补量
    boolean animyaninend = false; //进入动画Y轴是否结束
    float alpha = 5; //背景初始亮度
    public static boolean toclose = false; //关闭动画所需参数 还有bug暂时没做好先留个接口
    float[] typeanimto = new float[16];

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        //toclose = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        beterspeedinfps = 120.0f / mc.getDebugFPS();

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

        if (ClickGui.typeanimx[0] == 0) {
            ClickGui.typeanimx[0] = -4;
        }
        if (ClickGui.typeanimx[1] == 0) {
            ClickGui.typeanimx[1] = font1.getStringWidth(Category.Combat.name()) + 4;
        }

        float fontrendery = y + (blueheight - font1.getStringHeight(ClickGui.curType.name())) / 2 + 1;
        for (Category c : Category.values()) {
            //RenderUtils.drawRect((int) rx, (int) ry, (int) (rx + 0.3f * width), (int) (ry + 0.0625f * height), new Color(255, 255, 255).getRGB());
            font1.drawString(c.name(), rx, fontrendery, new Color(255, 255, 255).getRGB());

            if (c == ClickGui.curType) {
                typeanimto[0] = rx - 4 - x;
                typeanimto[1] = rx + font1.getStringWidth(c.name()) + 4 - x;
            }

            if (isHovered(rx - 4, ry, rx + font1.getStringWidth(c.name()) + 4, ry + blueheight, mouseX, mouseY) && Mouse.isButtonDown(0) && !keydown) {
                if(c == Category.Combat){
                    check = 2;
                }else if(c == Category.Movement){
                    check = 3;
                }else if(c == Category.Player){
                    check = 4;
                }else if(c == Category.Render){
                    check = 5;
                }else if(c == Category.World){
                    check = 6;
                }
                keydown = true;
                keydownX = (int) (mouseX - x);
                keydownY = (int) (mouseY - y);
            }

            rx += font1.getStringWidth(c.name()) + 8;
        }

        float[] speed = new float[8];
        if(ClickGui.typeanimx[0] > typeanimto[0] &&
                ClickGui.typeanimx[1] > typeanimto[1]){
            speed[0] = 8;
            speed[1] = 12;
        }else{
            speed[0] = 12;
            speed[1] = 8;
        }
        ClickGui.typeanimx[0] = toanim(ClickGui.typeanimx[0], typeanimto[0], speed[0], 0.1f);
        ClickGui.typeanimx[1] = toanim(ClickGui.typeanimx[1], typeanimto[1], speed[1], 0.1f);
        RenderUtils.drawRect((int) (ClickGui.typeanimx[0] + x), (int) ry, (int) (ClickGui.typeanimx[1] + x), (int) (ry + blueheight), new Color(255, 255, 255, 128).getRGB());

        rx = x + 5;
        float rxend = xend - 5;
        ry += 16;
        for (Module m : ModuleManager.getModules()) {
            if (m.getModuletype() == ClickGui.curType) {
                if(m.isToggle()){
                    RenderUtils.drawRoundRect((int) rx, (int) ry, (int) rxend, (int) (ry + 16),1,new Color(255,255,255));
                }else{
                    RenderUtils.drawRoundRect((int) rx, (int) ry, (int) rxend, (int) (ry + 16),1,new Color(255,255,255,128));
                }
                float fontytemp = ry + (20 - font.getStringHeight(m.getName())) / 2 - 1;
                float fontxtemp = rx + 5;
                if(m.isToggle()){
                    font.drawString(m.getName(),fontxtemp,fontytemp,new Color(0,0,0).getRGB());
                }else{
                    font.drawString(m.getName(),fontxtemp,fontytemp,new Color(0,0,0,128).getRGB());
                }

                ry += 20;
            }
        }

        check(mouseX, mouseY);

        if (toclose && alpha == alphato && animyto == animgety) {
            toclose = false;
        }
    }

    public float toanim(float now, float end, float multiplier, float min) {
        float speed = Math.max((Math.abs(now - end) / multiplier), min) * beterspeedinfps;
        if (now < end) {
            if (now + speed > end) {
                now = end;
            } else {
                now += speed;
            }
        } else if (now > end) {
            if (now - speed < end) {
                now = end;
            } else {
                now -= speed;
            }
        }
        return now;
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

        if (!Mouse.isButtonDown(0)) {
            check = 0;
            keydown = false;
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

        if (check == 1) {
            ClickGui.x = mouseX - keydownX;
            ClickGui.y = mouseY - keydownY;
        }

        if (check == 2){
            ClickGui.curType = Category.Combat;
        }else if (check == 3){
            ClickGui.curType = Category.Movement;
        }else if (check == 4){
            ClickGui.curType = Category.Player;
        }else if (check == 5){
            ClickGui.curType = Category.Render;
        }else if (check == 6){
            ClickGui.curType = Category.World;
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
