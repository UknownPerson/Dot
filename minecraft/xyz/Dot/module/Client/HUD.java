package xyz.Dot.module.Client;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.event.events.world.EventPostUpdate;
import xyz.Dot.event.events.world.EventPreUpdate;
import xyz.Dot.event.events.world.EventTick;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.Helper;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.TimerUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HUD extends Module {

    public HUD() {
        super("HUD", Keyboard.KEY_NONE, Category.Client);
    }

    private TimerUtil timer = new TimerUtil();
    double posx, posy, posz, lastpx = 0, lastpy = 0, lastpz = 0;
    float movespeed;
    int startTime = 0;
    float range = 0;
    //float[] bps;
    float[] bps = new float[128];
    int nums = 0;
    float beterspeedinfps;

    public float threegenhao(float num) {
        Scanner sc = new Scanner(String.valueOf(num));
        double a = sc.nextDouble();
        double l = -10000, r = 10000;
        while (r - l > 1e-8) {
            double mid = (l + r) / 2;
            if (mid * mid * mid >= a) r = mid;
            else l = mid;
        }
        return (float) r;
    }

    @EventHandler
    private void onPreUpdate(EventPreUpdate e) {

        posx = mc.thePlayer.posX;
        posy = mc.thePlayer.posY;
        posz = mc.thePlayer.posZ;
        int overTime = mc.thePlayer.ticksExisted;
        float time = (overTime - startTime) / 20.0f;
        startTime = mc.thePlayer.ticksExisted;
        //float move = threegenhao((float) (Math.pow(Math.abs(posx - lastpx), 3) + Math.pow(Math.abs(posy - lastpy), 3) + Math.pow(Math.abs(posz - lastpz), 3)));
        float move = (float) Math.sqrt((float) (Math.pow(Math.abs(posx - lastpx), 2) + Math.pow(Math.abs(posz - lastpz), 2)));
        movespeed = Math.round((move / time) * 100) / 100.0f;
        range += move;
        lastpx = posx;
        lastpy = posy;
        lastpz = posz;
        if (movespeed < 0 || time == 0) {
            movespeed = 0;
        }
        bps[nums] = movespeed;

        // times[nums] = startTime;
        if (++nums > 100) {
            nums -= 100;
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

    @EventHandler
    public void renderHud(EventRender2D event) {

        if (mc.gameSettings.showDebugProfilerChart) {
            return;
        }
        CFontRenderer font = FontLoaders.normalfont10;
        CFontRenderer font1 = FontLoaders.normalfont16;
        String CName = Client.instance.client_name;

        ArrayList<Module> list = new ArrayList<Module>();
        for (Module m : ModuleManager.getModules()) {
            list.add(m);
        }
        list.sort((o1, o2) -> font1.getStringWidth(o2.getName()) - font1.getStringWidth(o1.getName()));
        int StartX = 20;
        int StartY = 25;
        RenderUtils.drawRect(StartX, StartY + 12, StartX + 64, StartY + 56, new Color(0, 0, 0, 64).getRGB());
        RenderUtils.drawRect(StartX, StartY, StartX + 64, StartY + 12, new Color(64, 128, 255, 200).getRGB());

        RenderUtils.drawFilledCircle(StartX + 6, StartY + 6, 3, new Color(255, 0, 0,32));
        RenderUtils.drawFilledCircle(StartX + 7, StartY + 5, 3, new Color(255, 0, 0,32));
        RenderUtils.drawFilledCircle(StartX + 7, StartY + 7, 3, new Color(255, 0, 0,32));

        RenderUtils.drawFilledCircle(StartX + 10, StartY + 6, 3, new Color(0, 0, 255,32));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 5, 3, new Color(0, 0, 255,32));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 7, 3, new Color(0, 0, 255,32));

        RenderUtils.drawFilledCircle(StartX + 7, StartY + 6, 3, new Color(255, 0, 0,128));
        //RenderUtils.drawFilledCircle(StartX + 8, StartY + 6, 3, new Color(0, 255, 0,128));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 6, 3, new Color(0, 0, 255,128));


        font1.drawString(CName, StartX + 14, StartY + 4, new Color(255, 255, 255).getRGB());
        StartY += 20;
        font1.drawString("FPS: " + mc.getDebugFPS(), StartX + 5, StartY, new Color(255, 255, 255).getRGB());
        StartY += 12;
        String ping = String.valueOf(mc.getNetHandler().getPlayerInfo(Minecraft.thePlayer.getUniqueID()).getResponseTime());
        if (ping.equals("0")) {
            ping = "Failed";
        }
        font1.drawString("PING: " + ping, StartX + 5, StartY, new Color(255, 255, 255).getRGB());
        StartY += 12;
        font1.drawString("BPS: " + movespeed, StartX + 5, StartY, new Color(255, 255, 255).getRGB());

        int StartXspeed = 20;
        int StartYspeed = 96;
        RenderUtils.drawRect(StartXspeed, StartYspeed + 12, StartXspeed + 96, StartYspeed + 62, new Color(0, 0, 0, 64).getRGB());
        RenderUtils.drawRect(StartXspeed, StartYspeed, StartXspeed + 96, StartYspeed + 12, new Color(64, 128, 255, 200).getRGB());
        int numsm = nums - 1;
        float xnum = 0.5f;
        float[] avglist = new float[100];
        int avgnum = 0;
        for (int i = 0; i <= 95; i++) {
            int rank = numsm - i;
            if (rank < 1) {
                rank += 100;
            }
            float mspeed = bps[rank];
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
            float mspeed = bps[rank];

            RenderUtils.drawRect(StartXspeed + 95 - i, (int) (StartYspeed + (62 - (mspeed / xnum))), StartXspeed + 96 - i, StartYspeed + 62, new Color(255, 255, 255, 128).getRGB());
        }

        float avg = 0;
        for (float x : avglist) {
            avg += x;
        }
        avg = Math.round((avg / 96) * 100) / 100.0f;
        ;

        String mtext = "BPS.AVG: " + avg;
        font1.drawString(mtext, StartXspeed + 5, StartYspeed + 4, new Color(255, 255, 255).getRGB());
        //font1.drawString(mtext, StartXspeed + 96 - font1.getStringWidth(mtext) - 5, StartYspeed + 4, new Color(255, 255, 255).getRGB());

        float y = 15;
        int num = 0;
        for (Module m : list) {
            if (!m.isToggle() && m.getAnimY() == 0) {
                continue;
            }

            float yaddto;
            float endx = RenderUtils.width() - 10;
            float x = endx - font1.getStringWidth(m.getName());
            beterspeedinfps = 120.0f / mc.getDebugFPS();

            if (m.isToggle()) {
                yaddto = 15;
            } else {
                yaddto = 0;
            }

            float yadtemp = 15;

            m.setAnimY(toanim(m.getAnimY(), yaddto, 10, 0.1f));
            float yadd = m.getAnimY();
            RenderUtils.drawRect((int) x - 3, (int) y, (int) (endx + 3), (int) (y + yadd), new Color(0, 0, 0, 64).getRGB());
            //RenderUtils.drawRect((int) (endx + 2), (int) y, (int) (endx + 2 + 1), (int) (y + yadd), rainbow.getRGB());
            if (yadd > font1.getStringHeight(m.getName())) {
                int alpha = Math.round(((yadd - font1.getStringHeight(m.getName())) / (yadtemp - 6.0f)) * 255);

                if (alpha < 5) {
                    alpha = 5;
                } else if (alpha > 255) {
                    alpha = 255;
                }

                font1.drawString(m.getName(), x, y + (yadd - font1.getStringHeight(m.getName())) / 2 + 1.0f, new Color(255, 255, 255, alpha).getRGB());
                //font1.drawString(m.getName(), x, y + (yadd - font1.getStringHeight(m.getName())) / 2 + 1.0f, new Color(rainbow.getRed(), rainbow.getGreen(), rainbow.getBlue(), alpha).getRGB());
            }
            y += yadd;
        }
    }
}