package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class NameTag extends Module {

    public NameTag() {
        super("NameTag", Keyboard.KEY_NONE, Category.Render);
    }

}
