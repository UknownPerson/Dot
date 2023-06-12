package xyz.Dot.module.Cheat;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;

public class HitBox extends Module {
    public static Setting size = new Setting(ModuleManager.getModuleByName("HitBox"), "Size", 0.1d, 0.1d, 1.0d, 0.05d);
    public HitBox() {
        super("HitBox", Keyboard.KEY_NONE, Category.Cheat);
        this.addValues(size);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if(!ModuleManager.SigmaMode){
            this.setToggle(false);
        }
    }
}