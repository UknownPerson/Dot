package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;

public class OldBlockRender extends Module {
    public static Setting dig = new Setting(ModuleManager.getModuleByName("OldBlockRender"), "DigRender", true);
    public OldBlockRender() {
        super("OldBlockRender", Keyboard.KEY_NONE, Category.Render);
        this.addValues(dig);
    }
}
