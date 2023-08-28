package xyz.Dot.ui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import xyz.Dot.Client;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Client.MusicPlayer;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;


public class MusicPlayerUI extends GuiScreen {
    int width;
    int height;
    int x;
    int y;
    int keydownX, keydownY;
    int check;

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        Client.instance.getModuleManager().getModuleByName("MusicPlayer").setToggle(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        width = (int) MusicPlayer.width;
        height = (int) MusicPlayer.height;
        x = (int) MusicPlayer.x;
        y = (int) MusicPlayer.y;
        RenderUtils.drawRoundRect(x, y, x + width, y + height, 3, new Color(255, 255, 255));
        RenderUtils.drawHalfRoundRect(x, y, x + width, y + 12, 3, CustomColor.getColor());
        if (isHovered(x, y, x + width, y + 12, mouseX, mouseY) && Mouse.isButtonDown(0) && check == 0) {
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
            check = 1;
        }

        if(!Mouse.isButtonDown(0)){
            check = 0;
        }

        if(check == 1){
            MusicPlayer.x = mouseX -keydownX;
            MusicPlayer.y = mouseY - keydownY;
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
