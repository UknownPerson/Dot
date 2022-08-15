package xyz.Dot;

import org.apache.logging.log4j.core.appender.FileManager;
import org.lwjgl.opengl.Display;
import xyz.Dot.command.CommandManager;
import xyz.Dot.file.CustomFileManager;
import xyz.Dot.log.Log_Dot;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.event.EventBus;
import xyz.Dot.setting.Setting;
import xyz.Dot.setting.SettingManager;
import xyz.Dot.ui.FontLoaders;

public enum Client {
    instance;
    public String client_name = "Dot";
    public String client_version = "0.1";
    public boolean inDevelopment = true;
    public EventBus eventmanger;
    public ModuleManager modulemanager;
    public SettingManager settingmanager;

    public CommandManager commandmanager;
    public CustomFileManager customfilemanager;
    public FontLoaders fontloaders;

    public void run(){

        Log_Dot.info("Client Start");
        eventmanger = new EventBus();
        modulemanager = new ModuleManager();
        settingmanager = new SettingManager();
        commandmanager = new CommandManager();
        customfilemanager = new CustomFileManager();
        fontloaders = new FontLoaders();
        String title = client_name + " " + client_version + " " + getDevMode() + "- Minecraft 1.8.9";
        Display.setTitle(title);
        modulemanager.loadModule();
        commandmanager.run();
        customfilemanager.loadFiles();
    }

    public void stop(){

        save();
        Log_Dot.sava_Log();
        Log_Dot.info("Client Close");

    }

    public void save(){

        Log_Dot.info("Client Save");
        customfilemanager.saveFiles();

    }

    public String getDevMode(){

        if(inDevelopment){
            return "Dev ";
        }else{
            return "";
        }

    }

    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }
}
