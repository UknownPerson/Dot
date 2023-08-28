package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Notification;

public class Notifications extends Module {
    public static Setting fade = new Setting(Client.instance.getModuleManager().getModuleByName("Notifications"), "Fade", true);
    public Notifications() {
        super("Notifications", Keyboard.KEY_NONE, Category.Client);
        this.addValues(fade);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Notification.notifications.clear();
    }
}
