package xyz.Dot.module.Misc;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.ui.Custom;

public class KeyStrokes extends Module {
    public KeyStrokes() {
        super("KeyStrokes", Keyboard.KEY_NONE, Category.Misc);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if (Custom.open) {
            Custom.ks();
        }
        Custom.drawKeyStrokes();
    }
}
