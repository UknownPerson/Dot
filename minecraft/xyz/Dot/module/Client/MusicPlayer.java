package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.ui.ClickUI;
import xyz.Dot.ui.MusicPlayerUI;

public class MusicPlayer extends Module {
    public static float width = 150.0f, height = 300.0f;
    public static float x = 0.0f, y = 0.0f;

    public MusicPlayer() {
        super("MusicPlayer", Keyboard.KEY_NONE, Category.Client);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new MusicPlayerUI());
    }
}
