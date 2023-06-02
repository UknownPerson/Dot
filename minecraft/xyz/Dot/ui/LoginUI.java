package xyz.Dot.ui;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.SystemUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.Objects;

public class LoginUI extends GuiScreen {
    float dotsizetemp = 10;
    boolean dotsizetobig = false;
    boolean dotsizetobigok = false;
    float alpha = 0;
    String hwid = "null";

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        CFontRenderer font = FontLoaders.normalfont16;

        if (!dotsizetobigok) {
            RenderUtils.drawRect(0, 0, RenderUtils.width(), RenderUtils.height(), new Color(0, 0, 0).getRGB());
            String welcometext = "Welcome to the Dot!";
            font.drawString(welcometext, (RenderUtils.width() - font.getStringWidth(welcometext)) / 2, RenderUtils.height() / 2 + 10, new Color(255, 255, 255).getRGB());
            RenderUtils.drawFilledCircle(RenderUtils.width() / 2, RenderUtils.height() / 2 - 10, (int) dotsizetemp, new Color(255, 255, 255));
        }

        if (Mouse.isButtonDown(0)) {
            dotsizetobig = true;
        }
        if (dotsizetobig && !dotsizetobigok) {
            dotsizetemp = RenderUtils.toanim1(dotsizetemp, 10, Math.max(RenderUtils.width(), RenderUtils.height()), 16, 1f);
            if (dotsizetemp == Math.max(RenderUtils.width(), RenderUtils.height())) {
                dotsizetobigok = true;
            }
        }
        if (dotsizetobigok) {
            RenderUtils.drawRect(0, 0, RenderUtils.width(), RenderUtils.height(), new Color(255, 255, 255).getRGB());

            int x1 = RenderUtils.width() * 1080 <= RenderUtils.height() * 1920 ? (int) ((RenderUtils.width() - (7620 * RenderUtils.height() / 4320f)) / 2) : 0;
            int twidth = RenderUtils.width() * 1080 <= RenderUtils.height() * 1920 ? (int) (7620 * RenderUtils.height() / 4320f) : RenderUtils.width();

            int y1 = RenderUtils.width() * 1080 <= RenderUtils.height() * 1920 ? 0 : (int) ((RenderUtils.height() - (4320 * RenderUtils.width() / 7620f)) / 2);
            int theight = RenderUtils.width() * 1080 <= RenderUtils.height() * 1920 ? RenderUtils.height() : (int) (4320 * RenderUtils.width() / 7620f);
            alpha = RenderUtils.toanim(alpha, 255, 32, 0.1f);
            RenderUtils.drawImage(ImageLoader.tohru1, x1, y1, twidth, theight, new Color(255, 255, 255, (int) alpha));

            int goldx = (int) (RenderUtils.width() * 0.6);
            int goldy = (int) (RenderUtils.height() * 0.6);
            int goldy1 = (int) (RenderUtils.height() - 75);

            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtils.doGlScissor(goldx, goldy1, (int) Math.max(goldx + RenderUtils.width() * 0.3f, goldx + 100), (int) Math.max((goldy1 + RenderUtils.width() * 0.03f), goldy1 + 10));

            RenderUtils.drawRoundRect(goldx, goldy1, (int) Math.max(goldx + RenderUtils.width() * 0.3f, goldx + 100), (int) Math.max((goldy1 + RenderUtils.width() * 0.03f), goldy1 + 10), 4, new Color(255, 255, 255, 200));
            int texty = (int) ((goldy1 + Math.max((goldy1 + RenderUtils.width() * 0.03f), goldy1 + 10) - font.getStringHeight("sdasdadsadasdad")) / 2 + 1);

            if(hwid.contains("null")){
                hwid = SystemUtils.getHWID();
            }
            font.drawString(hwid,goldx + 5, texty,new Color(0,0,0).getRGB());

            GL11.glDisable(GL11.GL_SCISSOR_TEST);

            RenderUtils.drawRoundRect(goldx - 35, goldy1, goldx - 5, (int) Math.max((goldy1 + RenderUtils.width() * 0.03f), goldy1 + 10), 4, new Color(CustomColor.getColor().getRed(),CustomColor.getColor().getGreen(),CustomColor.getColor().getBlue(),(int)alpha));
            font.drawString("HWID",goldx - 30, texty,new Color(255,255,255).getRGB());

            RenderUtils.drawRoundRect((int) Math.max(goldx + RenderUtils.width() * 0.3f, goldx + 100) + 5, goldy1, (int) Math.max(goldx + RenderUtils.width() * 0.3f, goldx + 100) + 35, (int) Math.max((goldy1 + RenderUtils.width() * 0.03f), goldy1 + 10), 4, new Color(CustomColor.getColor().getRed(),CustomColor.getColor().getGreen(),CustomColor.getColor().getBlue(),(int)alpha));
            font.drawString("Copy",(int) Math.max(goldx + RenderUtils.width() * 0.3f, goldx + 100) + 11, texty,new Color(255,255,255).getRGB());
            if (isHovered((int) Math.max(goldx + RenderUtils.width() * 0.3f, goldx + 100) + 5, goldy1, (int) Math.max(goldx + RenderUtils.width() * 0.3f, goldx + 100) + 35, (int) Math.max((goldy1 + RenderUtils.width() * 0.03f), goldy1 + 10), mouseX, mouseY) && Mouse.isButtonDown(0)) {
                setSysClipboardText(hwid);
            }

            RenderUtils.drawRoundRect(RenderUtils.width() - 35, RenderUtils.height() - 20, RenderUtils.width() - 5, RenderUtils.height() - 5, 4, CustomColor.getColor());
            font.drawString("Skip", RenderUtils.width() - 28, RenderUtils.height() - 15, new Color(255, 255, 255).getRGB());
            if (isHovered(RenderUtils.width() - 35, RenderUtils.height() - 20, RenderUtils.width() - 5, RenderUtils.height() - 5, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                mc.displayGuiScreen(new GuiMainMenu());
            }
        }

    }

    public static void setSysClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        if(!Objects.equals(getClipboardString(), writeMe)){
            clip.setContents(tText, null);
        }
    }

    public static String getClipboardString() {
        //获取系统粘贴板
        //Toolkit类：Abstract Window Toolkit的所有实际实现的抽象超类。 Toolkit类的子类用于将各种组件绑定到特定的本机Toolkit实现。
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //获取封装好的data数据
        Transferable ts = clipboard.getContents(null);
        if (ts != null) {
            // 判断剪贴板中的内容是否支持文本
            if (ts.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String data = (String) ts.getTransferData(DataFlavor.stringFlavor);
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
