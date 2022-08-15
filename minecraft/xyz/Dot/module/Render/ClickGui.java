package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.ui.ClickUI;

public class ClickGui extends Module {
    public static float width = 300.0f, height = 200.0f;
    public static float x = 0.0f, y = 0.0f;
    public static Category curType = Category.Combat;
    public static float[] typeanimx = new float[16];

    public ClickGui() {
        super("ClickGui", Keyboard.KEY_RSHIFT, Category.Render);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.mc.displayGuiScreen(new ClickUI());
        this.setToggle(false);
    }
}
