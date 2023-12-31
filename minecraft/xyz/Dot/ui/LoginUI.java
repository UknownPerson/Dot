package xyz.Dot.ui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.Dot.Client;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.SystemUtils;
import xyz.Dot.utils.UserUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoginUI extends GuiScreen {
    float dotsizetemp = 10;
    boolean dotsizetobig = false;
    boolean dotsizetobigok = false;
    float alpha = 0;
    String hwid = "null";
    private List<GuiTextField> textFieldList = Lists.<GuiTextField>newArrayList();

    @Override
    public void initGui() {
        super.initGui();
        textFieldList.clear();
        this.textFieldList.add(new GuiTextField(0, fontRendererObj, RenderUtils.width() / 2 - 100, RenderUtils.height() / 5, 200, 20));
    }

    @Override
    public void onGuiClosed() {
    }

    @Override
    protected void keyTyped(char keyChar, int keyCode) throws IOException {
        super.keyTyped(keyChar, keyCode);

        for (GuiTextField tf : textFieldList) {
            if (tf.isFocused()) {
                tf.textboxKeyTyped(keyChar, keyCode);
            }
        }
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

            int imagex = 7620;
            int imagey = 4320;

            int x1 = RenderUtils.width() * imagey <= RenderUtils.height() * imagex ? (RenderUtils.width() - (imagex * RenderUtils.height() / imagey)) / 2 : 0;
            int twidth = RenderUtils.width() * imagey <= RenderUtils.height() * imagex ? imagex * RenderUtils.height() / imagey : RenderUtils.width();

            int y1 = RenderUtils.width() * imagey <= RenderUtils.height() * imagex ? 0 : (RenderUtils.height() - (imagey * RenderUtils.width() / imagex)) / 2;
            int theight = RenderUtils.width() * imagey <= RenderUtils.height() * imagex ? RenderUtils.height() : imagey * RenderUtils.width() / imagex;

            alpha = RenderUtils.toanim(alpha, 255, 32, 0.1f);
            RenderUtils.drawImage(ImageLoader.tohru1, x1, y1, twidth, theight, new Color(255, 255, 255, (int) alpha));

            int goldx = (int) (RenderUtils.width() * 0.6);
            int goldy = (int) (RenderUtils.height() * 0.6);
            int goldy1 = (int) (RenderUtils.height() - 75f);

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

            RenderUtils.drawRoundRect((int) RenderUtils.width() / 2 + 105, RenderUtils.height() / 5, (int) RenderUtils.width() / 2 + 105 + 50, (int) (RenderUtils.height() / 5)+ 20, 4, new Color(CustomColor.getColor().getRed(),CustomColor.getColor().getGreen(),CustomColor.getColor().getBlue(),(int)alpha));
            font.drawString("FreeLogin",(int) RenderUtils.width() / 2 + 111, RenderUtils.height() / 5 + 7,new Color(255,255,255).getRGB());

            if (isHovered((int) RenderUtils.width() / 2 + 105, RenderUtils.height() / 5, (int) RenderUtils.width() / 2 + 105 + 50, (int) (RenderUtils.height() / 5)+ 20, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                UserUtils.name = textFieldList.get(0).getText() + " - free";
                UserUtils.prefix = "Free";
                UserUtils.SigmaMode = false;
                if (ModuleManager.getModuleByName("IRC").isToggle()) {
                    ModuleManager.getModuleByName("IRC").setToggle(false);
                    ModuleManager.getModuleByName("IRC").setToggle(false);
                }
                mc.displayGuiScreen(new GuiMainMenu());
            }

            for (GuiTextField tf : textFieldList) {
                tf.drawTextBox();
            }

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
        //鑾峰彇绯荤粺绮樿创鏉�
        //Toolkit绫伙細Abstract Window Toolkit鐨勬墍鏈夊疄闄呭疄鐜扮殑鎶借薄瓒呯被銆� Toolkit绫荤殑瀛愮被鐢ㄤ簬灏嗗悇绉嶇粍浠剁粦瀹氬埌鐗瑰畾鐨勬湰鏈篢oolkit瀹炵幇銆�
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //鑾峰彇灏佽濂界殑data鏁版嵁
        Transferable ts = clipboard.getContents(null);
        if (ts != null) {
            // 鍒ゆ柇鍓创鏉夸腑鐨勫唴瀹规槸鍚︽敮鎸佹枃鏈�
            if (ts.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 鑾峰彇鍓创鏉夸腑鐨勬枃鏈唴瀹�
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
        for (GuiTextField tf : textFieldList) {
            tf.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
}
