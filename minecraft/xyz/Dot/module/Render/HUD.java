package xyz.Dot.module.Render;

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

public class HUD extends Module {

    public HUD() {
        super("HUD", Keyboard.KEY_NONE, Category.RENDER);
    }

    private TimerUtil timer = new TimerUtil();
    int rainbowTick = 0;
    int rainbowTick1 = 0;

    @EventHandler
    private void onUpdatePost(EventPostUpdate e) {
        rainbowTick1++;
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

        float titleStartX = 25;
        float titleStartY = 20;

        float drawVerX = titleStartX + 0.75f * font.getStringWidth(CName.substring(0, 2));
        float drawVerY = titleStartY + 1.2f * font.getStringHeight(CName);

        String ver = "- " + CVer + " " + Client.instance.getDevMode();

        font.drawString(CName, titleStartX + 1, titleStartY - 1, new Color(0, 0, 0).getRGB());
        font1.drawString(ver, drawVerX + 1, drawVerY - 1, new Color(0, 0, 0).getRGB());
        font.drawString(CName, titleStartX, titleStartY, new Color(255, 255, 255).getRGB());
        font1.drawString(ver, drawVerX, drawVerY, new Color(255, 255, 255).getRGB());

        float y = 15;
        int num = 0;
        for (Module m : ModuleManager.getModules()) {
            if (!m.isToggle() && m.getAnimY() == 0) {
                continue;
            }

            num += 1;
            rainbowTick = rainbowTick1 + num;

            while(rainbowTick > 50){
                rainbowTick -= 50;
            }

            Color rainbow = new Color(Color.HSBtoRGB((float) ((double) this.mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) (rainbowTick + (y - 4) / 12) / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));

            float yaddto;
            float endx = RenderUtils.width() - 10;
            float x = endx - font1.getStringWidth(m.getName());
            float beterspeedinfps = 120.0f / mc.getDebugFPS();

            if(m.isToggle()){
                yaddto = 12;
            }else{
                yaddto = 0;
            }

            float speedy = 0.8f * beterspeedinfps;
            if(String.valueOf(m.getAnimY()) == "NaN"){
                m.setAnimY(yaddto);
            }
            if (m.getAnimY() < yaddto) {
                m.setAnimY(m.getAnimY() + speedy);
            }
            if (m.getAnimY() > yaddto) {
                m.setAnimY(m.getAnimY() - speedy);
            }
            if ((Math.abs(m.getAnimY() - yaddto)) <= 0.8f) {
                m.setAnimY(yaddto);
            }

            float yadd = m.getAnimY();
            RenderUtils.drawRect((int) x - 1, (int) y, (int) (endx + 1), (int) (y + yadd), new Color(0, 0, 0, 64).getRGB());
            RenderUtils.drawRect((int) (endx + 1), (int) y, (int) (endx + 1 + 2), (int) (y + yadd), rainbow.getRGB());
            if(yadd > font1.getStringHeight(m.getName())) {
                int alpha = (int) ((yadd - font1.getStringHeight(m.getName())) / 6 * 255);
                //font1.drawString(m.getName(), x,y + (yadd - font1.getStringHeight(m.getName())) / 2 + 1.0f, new Color(255, 255, 255, alpha).getRGB());
                font1.drawString(m.getName(), x,y + (yadd - font1.getStringHeight(m.getName())) / 2 + 1.0f, new Color(rainbow.getRed(), rainbow.getGreen(), rainbow.getBlue(), alpha).getRGB());
            }
            y += yadd;
        }
    }
}