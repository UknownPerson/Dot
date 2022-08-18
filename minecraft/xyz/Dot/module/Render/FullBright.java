package xyz.Dot.module.Render;


import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.world.EventTick;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

import java.awt.*;

public class FullBright extends Module {
	public static float old;

	public FullBright() {
		super("FullBright", Keyboard.KEY_NONE, Category.Render);
	}

	@Override
	public void onEnable() {
		this.old = this.mc.gameSettings.saturation;
	}

	@EventHandler
	private void onTick(EventTick e) {
		this.mc.gameSettings.saturation = 1000f;
	}

	@Override
	public void onDisable() {
		this.mc.gameSettings.saturation = this.old;
	}
}
