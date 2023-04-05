package xyz.Dot.module.Client;

import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Notification;

import java.util.ArrayList;

public class TestModule extends Module {
    public static Setting testvalue = new Setting(ModuleManager.getModuleByName("TestModule"), "TV", 1.0d, 1.0d, 18.0d, 1.0d);
    static ArrayList<String> testmodes = new ArrayList<>(TestModes());
    static ArrayList<String> testmodes1 = new ArrayList<>(TestModes1());
    public static Setting testmode = new Setting(ModuleManager.getModuleByName("TestModule"), "ABC", "A", testmodes);
    public static Setting testmode1 = new Setting(ModuleManager.getModuleByName("TestModule"), "AB", "A", testmodes1);
    public static Setting testbool = new Setting(ModuleManager.getModuleByName("TestModule"), "TB", true);

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