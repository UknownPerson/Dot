package xyz.Dot.module.Cheat;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventPostRenderPlayer;
import xyz.Dot.event.events.rendering.EventPreRenderPlayer;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import org.lwjgl.opengl.GL11;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.ui.Notification;

public class Chams extends Module {
    public Chams() {
        super("Chams",  Keyboard.KEY_NONE, Category.Cheat) ;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(!ModuleManager.SigmaMode){
            Notification.sendClientMessage("You are not a Sigma user.You can't enable it.", Notification.Type.WARNING);
        }
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if(!ModuleManager.SigmaMode){
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