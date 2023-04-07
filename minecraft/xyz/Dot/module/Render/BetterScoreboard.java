package xyz.Dot.module.Render;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.RenderUtils;

public class BetterScoreboard extends Module {

    public static Setting x = new Setting(ModuleManager.getModuleByName("BetterScoreboard"), "X", -1.0d, 0.0d, 1.0d, 1.0d);
    public static Setting y = new Setting(ModuleManager.getModuleByName("BetterScoreboard"), "Y", -1.0d, 0.0d, 1.0d, 1.0d);
    public static Setting num = new Setting(ModuleManager.getModuleByName("BetterScoreboard"), "Number", false);
    public BetterScoreboard() {
        super("BetterScoreboard", Keyboard.KEY_NONE, Category.Render);
        this.addValues(x,y,num);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        x.setMaxValue(RenderUtils.width());
        y.setMaxValue(RenderUtils.height());
    }
}
