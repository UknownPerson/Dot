package xyz.Dot;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import xyz.Dot.api.PluginManager;
import xyz.Dot.command.CommandManager;
import xyz.Dot.custom.ComponentManager;
import xyz.Dot.event.EventBus;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.world.EventTick;
import xyz.Dot.file.CustomFileManager;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.module.Render.FullBright;
import xyz.Dot.setting.SettingManager;
import xyz.Dot.ui.FontLoaders;

public enum Client {
    instance;
    public String client_name = "Dot";
    public double client_version = 0.4;
    public boolean inDevelopment = false;

    protected Minecraft mc = Minecraft.getMinecraft();
    public EventBus eventmanger;
    public ModuleManager modulemanager;
    public SettingManager settingmanager;

    public CommandManager commandmanager;
    public CustomFileManager customfilemanager;
    public FontLoaders fontloaders;

    public PluginManager pluginManager;
    public ComponentManager componentManager;

    public void run(){

        eventmanger = new EventBus();
        modulemanager = new ModuleManager();
        settingmanager = new SettingManager();
        commandmanager = new CommandManager();
        customfilemanager = new CustomFileManager();
        fontloaders = new FontLoaders();
        componentManager = new ComponentManager();
        String title = client_name + " " + client_version + " " + getSigma() +getDevMode() + "- Minecraft 1.8.9";
        Display.setTitle(title);

        modulemanager.loadModule();
        pluginManager = new PluginManager();
        pluginManager.init();
        commandmanager.run();
        customfilemanager.loadFiles();

    }

    @EventHandler
    public static void onTick(EventTick e) {

    }

    public void stop() {

        save();
        mc.gameSettings.saturation = FullBright.old;

    }

    public void save(){

        customfilemanager.saveFiles();

    }

    public String getDevMode(){

        if(inDevelopment){
            return "Dev ";
        }else{
            return "";
        }

    }

    public String getSigma(){

        if(ModuleManager.SigmaMode){
            return "\u2211 ";
        }else{
            return "";
        }

    }
    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }
}
