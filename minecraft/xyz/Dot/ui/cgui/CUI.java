package xyz.Dot.ui.cgui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.Dot.Client;
import xyz.Dot.module.Category;
import xyz.Dot.module.Client.ClickGui;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Module;
import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;

public class CUI extends GuiScreen {
    static float x = 0, y = 0;
    static float width = 300, height = 200;
    int keydownX, keydownY;
    int check = 0; // 1涓荤晫闈� 2drag 3render 4misc 5cheat 6client 7 module
    boolean in = true;
    float openy = -114514;
    float categoryanimy = 0;
    float modulexanim = -114514;

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        if (in && openy != -114514) {
            y = openy;
        }
        Client.instance.getModuleManager().getModuleByName("ClickGui").setToggle(false);;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        CFontRenderer font = FontLoaders.normalfont16;

        if (in) {
            if (openy == -114514) {
                openy = y;
                y = RenderUtils.height();
            }
            y = RenderUtils.toanim(y, openy, 8, 0.1f);
            if (y == openy) {
                in = false;
            }
        }

        RenderUtils.drawRoundRect(x, y, x + width, y + height, 4, new Color(200, 200, 200));
        RenderUtils.drawRoundRect(x + 0.2f * width, y, x + width, y + height, 4, new Color(255, 255, 255));
        //RenderUtils.drawRoundRect(x, y, x + 0.25f * width, y + 0.2f * height, 4, new Color(255, 255, 255));

        float dragx = x + width - 12;
        float dragy = y + height - 12;
        Drag.draw(dragx, dragy, (isHovered(dragx, dragy, dragx + 10, dragy + 10, mouseX, mouseY) && check == 0) || check == 2);

        float userx = x + 4;
        float usery = y + height - 20;
        float userw = 0.2f * width - 5;
        User.draw(userx, usery, userw);

        int cy = (int) y;
        for (Category c : Category.values()) {
            int fx = (int) (x + 0.1 * width - font.getStringWidth(c.name()) / 2);
            int fy = (int) (cy + 0.25 * height);

            if (categoryanimy == 0) {
                categoryanimy = fy - 6 - y - 0.25f * height;
            }

            if (c == ClickGui.curType) {
                int categoryanimyto = (int) (fy - 6 - y - 0.25f * height);
                categoryanimy = RenderUtils.toanim(categoryanimy, categoryanimyto, 6, 0.1f);
                RenderUtils.drawRect((int) x, categoryanimy + y + 0.25f * height, (int) (x + 0.2 * width), categoryanimy + y + 0.25f * height + 16, new Color(0, 0, 0, 64).getRGB());
            }
            if (isHovered((int) x, fy - 6, (int) (x + 0.2 * width), fy + 10, mouseX, mouseY) && check == 0 && Mouse.isButtonDown(0)) {
                if (c == Category.Render) {
                    check = 3;
                } else if (c == Category.Misc) {
                    check = 4;
                } else if (c == Category.Cheat) {
                    check = 4;
                } else if (c == Category.Client) {
                    check = 4;
                }
                ClickGui.curType = c;
            }
            font.drawString(c.name(), fx, fy, new Color(0, 0, 0).getRGB());
            cy += 16;
        }

        float modulex = (int) x;
        int moduley = (int) y;

        int i = 0;

        for (Category c : Category.values()) {
            if (c == ClickGui.curType) {
                modulex = (float) (i * 0.8) * 100;
            }
            i++;
        }

        if (modulexanim == -114514) {
            modulexanim = modulex;
        }

        modulexanim = RenderUtils.toanim(modulexanim, modulex, 16, 0.1f);
        modulex = x - (modulexanim / 100) * width;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.doGlScissor((int) (x + 0.2f * width), y, x + width, (int) (y + height));

        for (Category c : Category.values()) {
            for (Module m : Client.instance.getModuleManager().getModules()) {
                if (m.getModuletype() == c) {

                    int cguiredto;
                    int cguigreento;
                    int cguiblueto;
                    int coloranimto;
                    if (m.isToggle()) {
                        cguiredto = CustomColor.getColor().getRed();
                        cguigreento = CustomColor.getColor().getGreen();
                        cguiblueto = CustomColor.getColor().getBlue();
                        coloranimto = 255;
                    } else {
                        cguiredto = 128;
                        cguigreento = 128;
                        cguiblueto = 128;
                        coloranimto = 175;
                    }

                    if (m.getCguired() == 0) {
                        m.setCguired(cguiredto);
                    }
                    if (m.getCguigreen() == 0) {
                        m.setCguigreen(cguigreento);
                    }
                    if (m.getCguiblue() == 0) {
                        m.setCguiblue(cguiblueto);
                    }
                    if (m.getColoranim() == 0) {
                        m.setColoranim(coloranimto);
                    }

                    m.setCguired(RenderUtils.toanim(m.getCguired(), cguiredto, 16, 0.1f));
                    m.setCguigreen(RenderUtils.toanim(m.getCguigreen(), cguigreento, 16, 0.1f));
                    m.setCguiblue(RenderUtils.toanim(m.getCguiblue(), cguiblueto, 16, 0.1f));
                    m.setColoranim(RenderUtils.toanim(m.getColoranim(), coloranimto, 16, 0.1f));

                    ClickGui.cmode.setCurrentMode("Classic");

                    Color canim = new Color((int) m.getCguired(), (int) m.getCguigreen(), (int) m.getCguiblue(), (int)(coloranimto / 1.5f));

                    Color fontcanim = new Color(0, 0, 0, (int) m.getColoranim());

                    RenderUtils.drawRoundRect(modulex + 0.2f * width + 16, moduley + 16, modulex + width - 16, moduley + 32, 4, canim);

                    if(isHovered((int) (x + 0.2f * width), y, x + width, (int) (y + height),mouseX,mouseY)){
                        if(isHovered(modulex + 0.2f * width + 16, moduley + 16, modulex + width - 16, moduley + 32,mouseX,mouseY) && check == 0 && Mouse.isButtonDown(0)){
                            check = 7;
                            m.toggle();
                        }
                    }

                    font.drawString(m.getName(), modulex + 0.22f * width + 16, moduley + 22, fontcanim.getRGB());
                    moduley += 20;

                }
            }
            modulex += 0.8 * width;
            moduley = (int) y;
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        if (isHovered(x, y, x + 0.2f * width, y + 0.25f * height, mouseX, mouseY) && check == 0 && Mouse.isButtonDown(0)) {
            check = 1;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (isHovered(dragx, dragy, dragx + 10, dragy + 10, mouseX, mouseY) && check == 0 && Mouse.isButtonDown(0)) {
            check = 2;
            keydownX = (int) (x + width - mouseX);
            keydownY = (int) (y + height - mouseY);
        }

        if (in) return;
        check(mouseX, mouseY);
    }

    public void check(int mouseX, int mouseY) {
        if (check == 1) {
            x = RenderUtils.toanim(x, Math.min(Math.max(mouseX - keydownX, 0), RenderUtils.width() - width), 2, 1f);
            y = RenderUtils.toanim(y, Math.min(Math.max(mouseY - keydownY, 0), RenderUtils.height() - height), 2, 1f);
        }

        if (check == 2) {
            width = RenderUtils.toanim(width, Math.min(Math.max(mouseX - x + keydownX, 300), RenderUtils.width() - x), 2, 1f);
            height = RenderUtils.toanim(height, Math.min(Math.max(mouseY - y + keydownY, 200), RenderUtils.height() - y), 2, 1f);
        }

        if (!Mouse.isButtonDown(0)) {
            check = 0;
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
