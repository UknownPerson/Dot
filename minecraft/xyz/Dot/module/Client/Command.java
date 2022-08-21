package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class Command extends Module {
    public Command() {
        super("Command", Keyboard.KEY_NONE, Category.Client);
    }
}
