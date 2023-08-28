package xyz.Dot;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import xyz.Dot.command.CommandManager;
import xyz.Dot.custom.ComponentManager;
import xyz.Dot.event.EventBus;
import xyz.Dot.file.CustomFileManager;
import xyz.Dot.module.Client.IRC;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.module.Render.FullBright;
import xyz.Dot.setting.SettingManager;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.UserUtils;

import java.io.IOException;

public enum Client {
    instance;
    public final String client_name = "Dot";
    public final double client_version = 0.6;
    public final boolean inDevelopment = true;

    private final Minecraft mc = Minecraft.getMinecraft();

    private EventBus eventBus;
    private ModuleManager modulemanager;
    private SettingManager settingmanager;
    private CommandManager commandmanager;
    private CustomFileManager customfilemanager;
    private FontLoaders fontloaders;
    private ComponentManager componentManager;

    private final Logger logger = LogManager.getLogger("Dot");

    public void run(){

        logger.info("Dot load!");
        eventBus = new EventBus();
        modulemanager = new ModuleManager();
        settingmanager = new SettingManager();
        commandmanager = new CommandManager();
        customfilemanager = new CustomFileManager();
        componentManager = new ComponentManager();
        String title = client_name + " " + client_version + " " + getSigma() +getDevMode() + "- Minecraft 1.8.9";
        Display.setTitle(title);

        Client.instance.getModuleManager().loadModule();
        commandmanager.run();
        customfilemanager.loadFiles();

    }

    public void stop() {

        logger.info("client shutdown.");
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

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }
}
