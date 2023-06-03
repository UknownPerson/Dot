package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.ui.ClickUI;

public class ClickGui extends Module {
    public static float width = 300.0f, height = 200.0f;
    public static float x = 0.0f, y = 0.0f;
    public static Category curType = Category.Render;
    public static Category lastcurType = Category.Render;
    public static float[] typeanimx = new float[8];
    public static Module settingmodule; // 参数module暂存
    public static boolean settingopen = false; // 参数界面是否开启

    public ClickGui() {
        super("ClickGui", Keyboard.KEY_RSHIFT, Category.Client);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new ClickUI());
    }
}
