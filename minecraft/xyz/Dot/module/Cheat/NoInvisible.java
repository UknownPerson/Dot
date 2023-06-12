package xyz.Dot.module.Cheat;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;

public class NoInvisible extends Module {
    public NoInvisible() {
        super("NoInvisible", Keyboard.KEY_NONE, Category.Cheat);
    }

    @Override
    public void onEnable() {
        super.onEnable();

    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if(!ModuleManager.SigmaMode){
            this.setToggle(false);
        }
    }
}
