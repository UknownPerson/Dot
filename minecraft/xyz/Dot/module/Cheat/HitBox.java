package xyz.Dot.module.Cheat;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.event.events.world.EventLivingUpdate;
import xyz.Dot.event.events.world.EventPreUpdate;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Notification;

public class HitBox extends Module {
    public static Setting size = new Setting(ModuleManager.getModuleByName("HitBox"), "Size", 0.1d, 0.1d, 1.0d, 0.05d);
    public HitBox() {
        super("HitBox", Keyboard.KEY_NONE, Category.Cheat);
        this.addValues(size);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(!ModuleManager.SigmaMode){
            Notification.sendClientMessage("You are not a Sigma user.You can't enable it.", Notification.Type.WARNING);
        }
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if(!ModuleManager.SigmaMode){
            this.setToggle(false);
        }
    }
}