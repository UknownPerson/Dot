package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class NoHurtCamera extends Module {

    public NoHurtCamera() {
        super("NoHurtCamera", Keyboard.KEY_NONE, Category.Render);
    }

}
