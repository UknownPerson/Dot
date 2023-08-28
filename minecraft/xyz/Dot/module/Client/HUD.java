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
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.Custom;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.TimerUtil;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;
import java.util.ArrayList;

public class HUD extends Module {
    public static Setting hudarraylist = new Setting(Client.instance.getModuleManager().getModuleByName("HUD"), "ArrayList", true);
    public static Setting arraylistColor = new Setting(Client.instance.getModuleManager().getModuleByName("HUD"), "ArrayList Color",new Color(-1));
    public static Setting blur = new Setting(Client.instance.getModuleManager().getModuleByName("HUD"), "Blur HUD", false);
    public static Setting shadow = new Setting(Client.instance.getModuleManager().getModuleByName("HUD"), "HUD Shadow", false);
    public static Setting transparent = new Setting(Client.instance.getModuleManager().getModuleByName("HUD"), "Transparent", false);
    static ArrayList<String> languages = new ArrayList<>(getLanguages());
    public static Setting lang = new Setting(Client.instance.getModuleManager().getModuleByName("HUD"), "Language", "English", languages){
        @Override
        public void setCurrentMode(String currentMode) {
            if(currentMode.equals("English")){
                Translator.getInstance().clearMessages();
            }else {
                Translator.getInstance().addMessages(HUD.class.getResourceAsStream("/language/" + currentMode.toLowerCase() + ".lang"));
            }
            super.setCurrentMode(currentMode);
        }
    };

    public static ArrayList<String> getLanguages() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("English");
        temp.add("Chinese");
        return temp;
    }
    public HUD() {
        super("HUD", Keyboard.KEY_NONE, Category.Client);
        this.addValues(lang,hudarraylist,arraylistColor,blur, shadow,transparent);
    }
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
        if (Client.instance.inDevelopment && false) {
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

        if (Client.instance.inDevelopment && false) {
            FontLoaders.normalfont16.drawString(t + "Tick/S", 100, 50, new Color(255, 255, 255).getRGB());
            FontLoaders.normalfont16.drawString(RenderUtils.ms + "ms", 100, 60, new Color(255, 255, 255).getRGB());
        }

        CFontRenderer font1 = FontLoaders.normalfont16;

        ArrayList<Module> list = new ArrayList<Module>();
        for (Module m : Client.instance.getModuleManager().getModules()) {
            list.add(m);
        }
        list.sort((o1, o2) -> font1.getStringWidth(Translator.getInstance().m(o2.getName())) - font1.getStringWidth(Translator.getInstance().m(o1.getName())));

        if(!hudarraylist.isToggle()){
            for (Module m : Client.instance.getModuleManager().getModules()) {
                m.setAnimY(0);
            }
            return;
        }

        float y = 15;
        boolean iamfirst = false;
        for (Module m : list) {
            if (!m.isToggle() && m.getAnimY() == 0) {
                continue;
            }

            float yaddto;
            float endx = RenderUtils.width() - 10;
            float x = endx - font1.getStringWidth(Translator.getInstance().m(m.getName()));

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

            if(HUD.blur.isToggle()){
                float finalY = y;
                ShaderManager.addBlurTask(new Runnable() {
                    @Override
                    public void run() {
                        RenderUtils.drawRect((int) x - 3, (int) finalY, (int) (endx + 3), (int) (finalY + yadd), new Color(0, 0, 0, 64).getRGB());
                    }
                });
            }

            RenderUtils.drawRect((int) x - 3, (int) y, (int) (endx + 3), (int) (y + yadd), new Color(0, 0, 0, 64).getRGB());
            //RenderUtils.drawRect((int) (endx + 2), (int) y, (int) (endx + 2 + 1), (int) (y + yadd), rainbow.getRGB());
            if (yadd > font1.getStringHeight(Translator.getInstance().m(m.getName()))) {
                int alpha = Math.round(((yadd - font1.getStringHeight(Translator.getInstance().m(m.getName()))) / (yadtemp - 6.0f)) * 255);
                if (alpha < 5) {
                    alpha = 5;
                } else if (alpha > 255) {
                    alpha = 255;
                }

                font1.drawString(Translator.getInstance().m(m.getName()), x, (float) (y + yadd * (1 - 0.618)), new Color(arraylistColor.getColor().getRed(),arraylistColor.getColor().getGreen(),arraylistColor.getColor().getBlue(), alpha).getRGB());
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
        for (Module m : Client.instance.getModuleManager().getModules()) {
            m.setAnimY(0);
        }
    }
}