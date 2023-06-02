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
    public static Setting color = new Setting(ModuleManager.getModuleByName("CustomColor"),"Color",new Color(0x29C6FF));
    public static float realred = 64;
    public static float realgreen = 128;
    public static float realblue =255;
    public CustomColor() {
        super("CustomColor", Keyboard.KEY_NONE, Category.Client);
        this.addValues(color);
    }
    public static Color getColor(){
        return new Color((int)realred,(int)realgreen,(int)realblue);
    }
}
