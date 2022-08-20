package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class NoBossBar extends Module {
    public NoBossBar() {
        super("NoBossBar", Keyboard.KEY_NONE, Category.Render);
    }
}
