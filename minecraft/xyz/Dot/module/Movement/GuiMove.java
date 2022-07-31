package xyz.Dot.module.Movement;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class GuiMove extends Module {

    public GuiMove(){
        super("GuiMove", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

}
