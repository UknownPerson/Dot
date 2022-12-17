package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.ui.Notification;

public class Notifications extends Module {
    public Notifications() {
        super("Notifications", Keyboard.KEY_NONE, Category.Client);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Notification.notifications.clear();
    }
}
