package xyz.Dot.module.Movement;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.world.EventPreUpdate;
import xyz.Dot.setting.Setting;

public class Sprint extends Module {
    public Sprint(){
        super("Sprint", Keyboard.KEY_NONE, Category.Movement);
    }
}
