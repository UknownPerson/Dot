package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class HUDPP extends Module {

    public HUDPP() {
        super("HUD++", Keyboard.KEY_NONE, Category.Render);
    }
}