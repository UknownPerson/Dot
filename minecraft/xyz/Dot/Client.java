package xyz.Dot;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import xyz.Dot.command.CommandManager;
import xyz.Dot.custom.ComponentManager;
import xyz.Dot.event.EventBus;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.world.EventTick;
import xyz.Dot.file.CustomFileManager;
import xyz.Dot.module.Client.IRC;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.module.Render.FullBright;
import xyz.Dot.setting.SettingManager;
import xyz.Dot.ui.CFont;
import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.UserUtils;

import java.io.IOException;
import java.util.ArrayList;

public enum Client {
    instance;
    public String client_name = "Dot";
    public double client_version = 0.6;
    public boolean inDevelopment = true;

    protected Minecraft mc = Minecraft.getMinecraft();
    public EventBus eventmanger;
    public ModuleManager modulemanager;
    public SettingManager settingmanager;

    public CommandManager commandmanager;
    public CustomFileManager customfilemanager;
    public FontLoaders fontloaders;

    public ComponentManager componentManager;
    private static final Logger logger = LogManager.getLogger();

    public void run(){

        logger.info("[Dot] Dot load!");
        eventmanger = new EventBus();
        modulemanager = new ModuleManager();
        settingmanager = new SettingManager();
        commandmanager = new CommandManager();
        customfilemanager = new CustomFileManager();
        componentManager = new ComponentManager();
        String title = client_name + " " + client_version + " " + getSigma() +getDevMode() + "- Minecraft 1.8.9";
        Display.setTitle(title);

        modulemanager.loadModule();
        commandmanager.run();
        customfilemanager.loadFiles();

    }

    public void stop() {

        logger.info("[Dot] stop!");
        save();
        mc.gameSettings.saturation = FullBright.old;

        try {
            IRC.sck.close();
            IRC.in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void save(){

        logger.info("[Dot] Save!");
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

        if(UserUtils.SigmaMode){
            return "\u2211 ";
        }else{
            return "";
        }

    }
    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }
}
