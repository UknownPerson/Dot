package xyz.Dot.module.Movement;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class Speed extends Module {

    public Speed(){
        super("Speed", Keyboard.KEY_NONE, Category.Movement);
    }

}
