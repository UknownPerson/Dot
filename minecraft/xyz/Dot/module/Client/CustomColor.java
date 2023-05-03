package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;

public class CustomColor extends Module {
    public static Setting r = new Setting(ModuleManager.getModuleByName("CustomColor"), "Red", 64d, 0d, 255d, 1d);
    public static Setting g = new Setting(ModuleManager.getModuleByName("CustomColor"), "Green", 128d, 0d, 255d, 1d);
    public static Setting b = new Setting(ModuleManager.getModuleByName("CustomColor"), "Blue", 255d, 0d, 255d, 1d);
    public static float realred = 64;
    public static float realgreen = 128;
    public static float realblue =255;
    public CustomColor() {
        super("CustomColor", Keyboard.KEY_NONE, Category.Client);
        this.addValues(r,g,b);
    }
    public static Color getColor(){
        return (new Color((int)realred,(int)realgreen,(int)realblue));
    }
}
