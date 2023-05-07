package xyz.Dot.module.Misc;

import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.misc.EventChat;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;

public class AutoGG extends Module {

    public AutoGG() {
        super("AutoGG",  Keyboard.KEY_NONE, Category.Misc) ;
    }

    @EventHandler
    public void onChat(EventChat c) {
        if (c.getMessage().contains("Winner - ") || c.getMessage().contains("第一名 - ") || c.getMessage().contains("胜利 -"))
            mc.thePlayer.sendChatMessage("/ac GG");
    }
}

