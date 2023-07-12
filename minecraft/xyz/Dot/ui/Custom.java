package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xyz.Dot.Client;
import xyz.Dot.custom.base.Component;

import java.io.IOException;


public class Custom extends GuiScreen {


    public static boolean open = false;

    public static float[] fucktest = new float[12800];

    @Override
    public void onGuiClosed() {
        open = false;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Minecraft.getMinecraft().gameSettings.showDebugProfilerChart) {
            return;
        }
        for (Component object : Client.instance.componentManager.components) {
            object.doDrag(mouseX, mouseY);
        }
    }


    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (Component object : Client.instance.componentManager.components) {
            object.mouseClick(mouseX, mouseY,mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

}
