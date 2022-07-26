package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;

public class HUD extends Module {

    public HUD(){
        super("HUD", Keyboard.KEY_NONE, Category.RENDER);
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

        String ver = "- " + CVer +  " " + Client.instance.getDevMode();

        font.drawString(CName,titleStartX,titleStartY,new Color(255,255,255).getRGB());
        font1.drawString(ver, drawVerX,drawVerY, new Color(255,255,255).getRGB());
        float y = 15;
        for (Module m : ModuleManager.getModules()) {
            if (!m.isToggle()){
                continue;
            }
            float x = RenderUtils.width() - font1.getStringWidth(m.getName()) - 10;
            font1.drawString(m.getName(),x,y,new Color(255,255,255).getRGB());
            y += 12;
        }
    }
}
