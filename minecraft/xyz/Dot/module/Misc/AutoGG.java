package xyz.Dot.module.Misc;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.misc.EventChat;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;

import java.util.ArrayList;

public class AutoGG extends Module {
    public static Setting server = new Setting(Client.instance.getModuleManager().getModuleByName("AutoGG"), "Server", "Hypixel", getServers());

    public AutoGG() {
        super("AutoGG",  Keyboard.KEY_NONE, Category.Misc);
        this.addValues(server);
    }

    @EventHandler
    public void onChat(EventChat c) {
        if (c.getMessage().contains("Winner - ") || c.getMessage().contains("\u7b2c\u4e00\u540d - ") || c.getMessage().contains("\u80dc\u5229 -"))
            if(server.getCurrentMode().equals("Hypixel")){
                mc.thePlayer.sendChatMessage("/ac GG");
            }else{
                mc.thePlayer.sendChatMessage("GG");
            }
    }

    public static ArrayList<String> getServers() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Hypixel");
        temp.add("Normal");
        return temp;
    }
}

