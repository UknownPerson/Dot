package xyz.Dot.module.Cheat;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventPostRenderPlayer;
import xyz.Dot.event.events.rendering.EventPreRenderPlayer;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.utils.UserUtils;

public class Chams extends Module {
    public Chams() {
        super("Chams",  Keyboard.KEY_NONE, Category.Cheat) ;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if(!UserUtils.SigmaMode){
            this.setToggle(false);
        }
    }

    @EventHandler
    private void preRenderPlayer(EventPreRenderPlayer e) {
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
    }

    @EventHandler
    private void postRenderPlayer(EventPostRenderPlayer e) {
        GL11.glDisable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)1100000.0f);
    }
}