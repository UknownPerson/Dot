package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.ClickUI;
import xyz.Dot.ui.cgui.CUI;

import java.util.ArrayList;

public class ClickGui extends Module {
    public static Setting cmode = new Setting(ModuleManager.getModuleByName("ClickGui"), "Mode", "New", ClickGuiModes());

    public static float width = 300.0f, height = 200.0f;
    public static float x = 0.0f, y = 0.0f;
    public static Category curType = Category.Render;
    public static Category lastcurType = Category.Render;
    public static float[] typeanimx = new float[8];
    public static Module settingmodule; // 参数module暂存
    public static boolean settingopen = false; // 参数界面是否开启

    public ClickGui() {
        super("ClickGui", Keyboard.KEY_RSHIFT, Category.Client);
        if(Client.instance.inDevelopment){
            this.addValues(cmode);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(cmode.getCurrentMode().equals("New")){
            mc.displayGuiScreen(new CUI());
        }else{
            mc.displayGuiScreen(new ClickUI());
        }
    }

    public static ArrayList<String> ClickGuiModes() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Classic");
        temp.add("New");
        return temp;
    }
}
