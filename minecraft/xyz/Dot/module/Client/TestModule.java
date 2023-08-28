package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Notification;

import java.util.ArrayList;

public class TestModule extends Module {
    public static Setting testvalue = new Setting(Client.instance.getModuleManager().getModuleByName("TestModule"), "TV", 1.0d, 1.0d, 18.0d, 0.5d);
    public static Setting testmode = new Setting(Client.instance.getModuleManager().getModuleByName("TestModule"), "ABC", "A", TestModes());
    public static Setting testmode1 = new Setting(Client.instance.getModuleManager().getModuleByName("TestModule"), "AB", "A", TestModes1());
    public static Setting testbool = new Setting(Client.instance.getModuleManager().getModuleByName("TestModule"), "TB", true);

    public TestModule() {
        super("TestModule", Keyboard.KEY_NONE, Category.Client);
        this.addValues(testvalue, testmode, testmode1, testbool);
    }

    public static ArrayList<String> TestModes() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("A");
        temp.add("B");
        temp.add("C");
        return temp;
    }

    public static ArrayList<String> TestModes1() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("A");
        temp.add("B");
        return temp;
    }
}