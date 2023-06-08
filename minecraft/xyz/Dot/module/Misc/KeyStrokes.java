package xyz.Dot.module.Misc;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Custom;
import xyz.Dot.utils.RenderUtils;

public class KeyStrokes extends Module {
    public static Setting x = new Setting(ModuleManager.getModuleByName("KeyStrokes"), "X", 20.0d, 0.0d, 1.0d, 1.0d);
    public static Setting y = new Setting(ModuleManager.getModuleByName("KeyStrokes"), "Y", 200.0d, 0.0d, 1.0d, 1.0d);
    public KeyStrokes() {
        super("KeyStrokes", Keyboard.KEY_NONE, Category.Misc);
        this.addValues(x,y);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        x.setMaxValue(RenderUtils.width());
        y.setMaxValue(RenderUtils.height());
        
        if (Custom.open) {
            Custom.ks();
        }
        Custom.drawKeyStrokes(HUD.blur.isToggle());
    }
}
