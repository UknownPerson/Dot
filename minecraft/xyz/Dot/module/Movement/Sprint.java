package xyz.Dot.module.Movement;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.world.EventPreUpdate;
import xyz.Dot.setting.Setting;

public class Sprint extends Module {
    Setting omni = new Setting(this, "omni" , false);
    public Sprint(){
        super("Sprint", Keyboard.KEY_P, Category.MOVEMENT);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {

        if (mc.thePlayer.getFoodStats().getFoodLevel() > 6 && !mc.thePlayer.isSneaking() && (omni.isToggle() ? mc.thePlayer.moving() : this.mc.thePlayer.moveForward > 0.0f)) {
            this.mc.thePlayer.setSprinting(true);
        }
    }
}
