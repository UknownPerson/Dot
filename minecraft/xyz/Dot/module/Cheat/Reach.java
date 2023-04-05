package xyz.Dot.module.Cheat;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;

public class Reach extends Module {
    public static Setting range = new Setting(ModuleManager.getModuleByName("Reach"), "Range", 3.0d, 3.0d, 4.5d, 0.1d);
    public Reach() {
        super("Reach", Keyboard.KEY_NONE, Category.Cheat);
        this.addValues(range);
    }
}