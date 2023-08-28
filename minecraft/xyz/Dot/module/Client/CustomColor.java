package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;

import java.awt.*;

public class CustomColor extends Module {
    public static Setting color = new Setting(Client.instance.getModuleManager().getModuleByName("CustomColor"),"Color",new Color(0x29C6FF));
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
