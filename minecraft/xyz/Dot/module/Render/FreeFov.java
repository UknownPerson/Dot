package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.world.EventPreUpdate;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class FreeFov extends Module {
    public FreeFov() {
        super("FreeFov", Keyboard.KEY_NONE, Category.Render);
    }

    @EventHandler
    private void onPreUpdate(EventPreUpdate e) {

        mc.thePlayer.render

    }

}
