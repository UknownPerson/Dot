package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xyz.Dot.Client;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;

public class Custom extends GuiScreen {

    static CFontRenderer font = FontLoaders.normalfont16;

    public static void drawDot() {
        Minecraft mc = Minecraft.getMinecraft();
        String CName = Client.instance.client_name;

        int StartX = 20;
        int StartY = 25;
        RenderUtils.drawRect(StartX, StartY + 12, StartX + 64, StartY + 56, new Color(0, 0, 0, 64).getRGB());
        RenderUtils.drawRect(StartX, StartY, StartX + 64, StartY + 12, new Color(64, 128, 255, 200).getRGB());

        RenderUtils.drawFilledCircle(StartX + 6, StartY + 6, 3, new Color(255, 0, 0, 32));
        RenderUtils.drawFilledCircle(StartX + 7, StartY + 5, 3, new Color(255, 0, 0, 32));
        RenderUtils.drawFilledCircle(StartX + 7, StartY + 7, 3, new Color(255, 0, 0, 32));

        RenderUtils.drawFilledCircle(StartX + 10, StartY + 6, 3, new Color(0, 0, 255, 32));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 5, 3, new Color(0, 0, 255, 32));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 7, 3, new Color(0, 0, 255, 32));

        RenderUtils.drawFilledCircle(StartX + 7, StartY + 6, 3, new Color(255, 0, 0, 128));
        //RenderUtils.drawFilledCircle(StartX + 8, StartY + 6, 3, new Color(0, 255, 0,128));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 6, 3, new Color(0, 0, 255, 128));


        font.drawString(CName, StartX + 14, StartY + 4, new Color(255, 255, 255).getRGB());
        StartY += 20;
        font.drawString("FPS: " + mc.getDebugFPS(), StartX + 5, StartY, new Color(255, 255, 255).getRGB());
        StartY += 12;
        String ping = String.valueOf(mc.getNetHandler().getPlayerInfo(Minecraft.thePlayer.getUniqueID()).getResponseTime());
        if (ping.equals("0")) {
            ping = "Failed";
        }
        font.drawString("PING: " + ping, StartX + 5, StartY, new Color(255, 255, 255).getRGB());
        StartY += 12;
        font.drawString("BPS: " + HUD.movespeed, StartX + 5, StartY, new Color(255, 255, 255).getRGB());

    }

    public static void drawBPSAVG() {
        int StartXspeed = 20;
        int StartYspeed = 96;
        RenderUtils.drawRect(StartXspeed, StartYspeed + 12, StartXspeed + 96, StartYspeed + 62, new Color(0, 0, 0, 64).getRGB());
        RenderUtils.drawRect(StartXspeed, StartYspeed, StartXspeed + 96, StartYspeed + 12, new Color(64, 128, 255, 200).getRGB());
        int numsm = HUD.nums - 1;
        float xnum = 0.5f;
        float[] avglist = new float[100];
        int avgnum = 0;
        for (int i = 0; i <= 95; i++) {
            int rank = numsm - i;
            if (rank < 1) {
                rank += 100;
            }
            float mspeed = HUD.bps[rank];
            while ((mspeed / xnum) > 50) {
                xnum += 0.5f;
            }

            avglist[avgnum] = mspeed;
            avgnum++;
        }

        String maxstring = String.valueOf((int) (xnum * 50));
        font.drawString(maxstring, StartXspeed + 96 - font.getStringWidth(maxstring) - 2, StartYspeed + 16, new Color(0, 0, 0, 128).getRGB());

        for (int i = 0; i <= 95; i++) {
            int rank = numsm - i;
            if (rank < 1) {
                rank += 100;
            }
            float mspeed = HUD.bps[rank];

            RenderUtils.drawRect(StartXspeed + 95 - i, (int) (StartYspeed + (62 - (mspeed / xnum))), StartXspeed + 96 - i, StartYspeed + 62, new Color(255, 255, 255, 128).getRGB());
        }

        float avg = 0;
        for (float x : avglist) {
            avg += x;
        }
        avg = Math.round((avg / 96) * 100) / 100.0f;

        String mtext = "BPS.AVG: " + avg;
        font.drawString(mtext, StartXspeed + 5, StartYspeed + 4, new Color(255, 255, 255).getRGB());
        int num = 0;
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {


    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

}
