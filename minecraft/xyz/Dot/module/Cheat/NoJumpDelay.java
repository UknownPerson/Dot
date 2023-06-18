package xyz.Dot.module.Cheat;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.utils.UserUtils;

public class NoJumpDelay extends Module {
	public NoJumpDelay() {
		super("NoJumpDelay", Keyboard.KEY_NONE, Category.Cheat);
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@EventHandler
	public void renderHud(EventRender2D event) {
		if(!UserUtils.SigmaMode){
			this.setToggle(false);
		}
	}
}
