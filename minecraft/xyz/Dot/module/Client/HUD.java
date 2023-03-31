package xyz.Dot.module.Client;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.event.events.world.EventPacketRecieve;
import xyz.Dot.event.events.world.EventTick;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.Custom;
import xyz.Dot.ui.FontLoaders;
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
    public static float movespeed;
    int startTime = 0;
    long startT = 0;
    float range = 0;
    //float[] bps;
    public static float[] bps = new float[128];
    public static int nums = 0;
    boolean fakemspeed = false;
    private TimerUtil timerUtil = new TimerUtil();
    float t;

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
    private void onTick(EventTick e) {

        posx = mc.thePlayer.posX;
        posy = mc.thePlayer.posY;
        posz = mc.thePlayer.posZ;
        int overTime = mc.thePlayer.ticksExisted;
        long overT = System.nanoTime();

        float time = (overTime - startTime) / 20.0f;
        t = (time / ((overT - startT) * 0.000000001f)) * 20;

        startTime = mc.thePlayer.ticksExisted;
        startT = System.nanoTime();

        long t1 = System.nanoTime();
        //float move = threegenhao((float) (Math.pow(Math.abs(posx - lastpx), 3) + Math.pow(Math.abs(posy - lastpy), 3) + Math.pow(Math.abs(posz - lastpz), 3)));
        float move = (float) Math.sqrt((float) (Math.pow(Math.abs(posx - lastpx), 2) + Math.pow(Math.abs(posz - lastpz), 2)));
        movespeed = Math.round((move / time) * 100) / 100.0f;
        if (Client.instance.inDevelopment) {
            movespeed = move / time;
        }
        range += move;
        lastpx = posx;
        lastpy = posy;
        lastpz = posz;
        if (movespeed < 0 || time == 0) {
            movespeed = 0;
        }
        if (fakemspeed) {
            movespeed = 0;
            fakemspeed = false;
        }
        bps[nums] = movespeed;

        int n = nums;
        if (n < 1) {
            n += 100;
        }
        Custom.fucktest[n] = 0;

        // times[nums] = startTime;
        if (++nums > 100) {
            nums -= 100;
        }
    }

    public float toanim(float now, float end, float multiplier, float min) {
        return RenderUtils.toanim(now, end, multiplier, min);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if (mc.gameSettings.showDebugProfilerChart) {
            return;
        }

        if (Client.instance.inDevelopment) {
            FontLoaders.normalfont16.drawString(t + "Tick/S", 100, 50, new Color(255, 255, 255).getRGB());
        }

        CFontRenderer font1 = FontLoaders.normalfont16;

        ArrayList<Module> list = new ArrayList<Module>();
        for (Module m : ModuleManager.getModules()) {
            list.add(m);
        }
        list.sort((o1, o2) -> font1.getStringWidth(o2.getName()) - font1.getStringWidth(o1.getName()));

        Custom.drawDot();
        Custom.drawBPSAVG();

        float y = 15;
        boolean iamfirst = false;
        for (Module m : list) {
            if (!m.isToggle() && m.getAnimY() == 0) {
                continue;
            }

            float yaddto;
            float endx = RenderUtils.width() - 10;
            float x = endx - font1.getStringWidth(m.getName());

            if (m.isToggle()) {
                yaddto = 15;
            } else {
                yaddto = 0;
            }

            float yadtemp = 15;

            if (!iamfirst) {
                m.setAnimY(toanim(m.getAnimY(), yaddto, 10, 0.1f));
            }

            if (2.5f * m.getAnimY() < yaddto) {
                iamfirst = true;
            }

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

                font1.drawString(m.getName(), x, (float) (y + yadd * (1 - 0.618)), new Color(255, 255, 255, alpha).getRGB());
                //font1.drawString(m.getName(), x, y + (yadd - font1.getStringHeight(m.getName())) / 2 + 1.0f, new Color(rainbow.getRed(), rainbow.getGreen(), rainbow.getBlue(), alpha).getRGB());
            }
            y += yadd;
        }
    }

    @EventHandler
    private void onPacket(EventPacketRecieve ep) {
        if (ep.getPacket() instanceof S08PacketPlayerPosLook) {
            fakemspeed = true;
        }
    }

    public void onDisable() {
        for (Module m : ModuleManager.getModules()) {
            m.setAnimY(0);
        }
    }
}