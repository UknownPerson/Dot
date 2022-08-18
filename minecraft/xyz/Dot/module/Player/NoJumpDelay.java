package xyz.Dot.module.Player;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class NoJumpDelay extends Module {
	public NoJumpDelay() {
		super("NoJumpDelay", Keyboard.KEY_NONE, Category.Player);
	}
}
