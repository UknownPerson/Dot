package xyz.Dot.module.Render;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.event.events.world.EventPostUpdate;
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
        super("HUD", Keyboard.KEY_NONE, Category.RENDER);
    }

    private TimerUtil timer = new TimerUtil();
    int rainbowTick = 0;
    int rainbowTick1 = 0;
    double posx,posy,posz,lastpx = 0,lastpy = 0,lastpz = 0;
    float movespeed;
    long startTime = 0;
    float range = 0;

    public float threegenhao(float num){
        Scanner sc = new Scanner(String.valueOf(num));
        double a = sc.nextDouble();
        double l = -10000,r = 10000;
        while(r - l > 1e-8) {
            double mid = (l + r) / 2;
            if(mid*mid*mid >= a) r = mid;
            else l = mid;
        }
        return (float) r;
    }

    @EventHandler
    private void onUpdatePost(EventPostUpdate e) {
        rainbowTick1++;
        long overTime = System.nanoTime();
        posx = mc.thePlayer.posX;
        posy = mc.thePlayer.posY;
        posz = mc.thePlayer.posZ;

        float time = (float) ((overTime - startTime) / Math.pow(10, 9));
        //float move = threegenhao((float) (Math.pow(Math.abs(posx - lastpx), 3) + Math.pow(Math.abs(posy - lastpy), 3) + Math.pow(Math.abs(posz - lastpz), 3)));
        float move = (float) Math.sqrt((float) (Math.pow(Math.abs(posx - lastpx), 2) + Math.pow(Math.abs(posz - lastpz), 2)));
        movespeed = Math.round((move / time) * 100) / 100.0f;

        range += move;

        lastpx = posx;
        lastpy = posy;
        lastpz = posz;
        startTime = System.nanoTime();
    }

    @EventHandler
    public void renderHud(EventRender2D event) {

        if (mc.gameSettings.showDebugProfilerChart) {
            return;
        }

        CFontRenderer font = FontLoaders.normalfont36;
        CFontRenderer font1 = FontLoaders.normalfont16;
        String CName = Client.instance.client_name;
        String CVer = Client.instance.client_version;

        ArrayList<Module> list = new ArrayList<Module>();
        for (Module m : ModuleManager.getModules()) {
            list.add(m);
        }
        list.sort((o1, o2) -> font1.getStringWidth(o2.getName()) - font1.getStringWidth(o1.getName()));

        int StartX = 20;
        int StartY = 25;
        RenderUtils.drawRect(StartX, StartY + 12, StartX + 64, StartY + 56, new Color(0,0,0,64).getRGB());
        RenderUtils.drawRect(StartX, StartY, StartX + 64, StartY + 12, new Color(64,128,255, 200).getRGB());
        RenderUtils.drawFilledCircle(StartX + 8, StartY + 6, 3, new Color(255,255,255));
        font1.drawString(Client.instance.client_name, StartX + 14, StartY + 4,new Color(255,255,255).getRGB());
        StartY += 20;
        font1.drawString("FPS: " + mc.getDebugFPS(),StartX + 5, StartY, new Color(255,255,255).getRGB());
        StartY += 12;
        String ping = String.valueOf(mc.getNetHandler().getPlayerInfo(Minecraft.thePlayer.getUniqueID()).getResponseTime());
        if(ping.equals("0")){
            ping = "Failed";
        }
        font1.drawString("PING: " + ping,StartX + 5, StartY, new Color(255,255,255).getRGB());
        StartY += 12;
        font1.drawString("BPS: " + movespeed,StartX + 5, StartY, new Color(255,255,255).getRGB());


        float y = 15;
        int num = 0;
        for (Module m : list) {
            if (!m.isToggle() && m.getAnimY() == 0) {
                continue;
            }

            num += 1;
            rainbowTick = rainbowTick1 + num;

            while(rainbowTick > 50){
                rainbowTick -= 50;
            }

            Color rainbow = new Color(Color.HSBtoRGB((float) ((double) this.mc.thePlayer.ticksExisted / 500.0 + Math.sin((double) (rainbowTick + (y - 4) / 12) / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));

            float yaddto;
            float endx = RenderUtils.width() - 10;
            float x = endx - font1.getStringWidth(m.getName());
            float beterspeedinfps = 120.0f / mc.getDebugFPS();

            if(m.isToggle()){
                yaddto = 12;
            }else{
                yaddto = 0;
            }

            float speedy = 1.0f * beterspeedinfps;
            if(String.valueOf(m.getAnimY()) == "NaN"){
                m.setAnimY(yaddto);
            }
            if (m.getAnimY() < yaddto) {
                m.setAnimY(m.getAnimY() + speedy);
            }
            if (m.getAnimY() > yaddto) {
                m.setAnimY(m.getAnimY() - speedy);
            }
            if ((Math.abs(m.getAnimY() - yaddto)) <= speedy) {
                m.setAnimY(yaddto);
            }

            float yadd = m.getAnimY();
            RenderUtils.drawRect((int) x - 2, (int) y, (int) (endx + 2), (int) (y + yadd), new Color(0, 0, 0, 64).getRGB());
            RenderUtils.drawRect((int) (endx + 2), (int) y, (int) (endx + 2 + 1), (int) (y + yadd), rainbow.getRGB());
            if(yadd > font1.getStringHeight(m.getName())) {
                int alpha = (int) ((yadd - font1.getStringHeight(m.getName())) / 6 * 255);
                //font1.drawString(m.getName(), x,y + (yadd - font1.getStringHeight(m.getName())) / 2 + 1.0f, new Color(255, 255, 255, alpha).getRGB());
                font1.drawString(m.getName(), x,y + (yadd - font1.getStringHeight(m.getName())) / 2 + 1.0f, new Color(rainbow.getRed(), rainbow.getGreen(), rainbow.getBlue(), alpha).getRGB());
            }
            y += yadd;
        }
    }
}