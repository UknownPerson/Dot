package xyz.Dot.module;

import xyz.Dot.Client;
import xyz.Dot.event.EventBus;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.misc.EventKey;
import xyz.Dot.module.Cheat.HitBox;
import xyz.Dot.module.Cheat.NoJumpDelay;
import xyz.Dot.module.Cheat.Reach;
import xyz.Dot.module.Cheat.ViewClip;
import xyz.Dot.module.Client.*;
import xyz.Dot.module.Misc.BetterSneak;
import xyz.Dot.module.Misc.KeyStrokes;
import xyz.Dot.module.Misc.Sprint;
import xyz.Dot.module.Misc.Teams;
import xyz.Dot.module.Render.*;

import java.util.ArrayList;

public class ModuleManager {
    static ArrayList<Module> modules;

    public ModuleManager(){
        modules = new ArrayList();
        EventBus.getInstance().register(this);
    }

    public void loadModule() {
        //Cheat
        this.addModule(new HitBox());
        this.addModule(new NoJumpDelay());
        this.addModule(new Reach());
        this.addModule(new ViewClip());

        //Client
        this.addModule(new ClickGui());
        this.addModule(new NoCommand());
        this.addModule(new HUD());
        this.addModule(new Notifications());
        if(Client.instance.inDevelopment){
            this.addModule(new TestModule());
        }

        //Misc
        this.addModule(new BetterSneak());
        this.addModule(new KeyStrokes());
        this.addModule(new Sprint());
        this.addModule(new Teams());

        //Render
        this.addModule(new BetterScoreboard());
        this.addModule(new BetterTabList());
        this.addModule(new EveryThingBlock());
        this.addModule(new FullBright());
        this.addModule(new HUDPP());
        this.addModule(new NameTag());
        this.addModule(new NoBossBar());
        this.addModule(new NoHurtCamera());
        this.addModule(new OldBlockRender());
        this.addModule(new SmoothZoom());
    }

    private void addModule(Module m){
        this.modules.add(m);
    }

    @EventHandler
    public void onKeyPress(EventKey e){
        for (Module m : modules) {
            if (m.getKeyBind() == e.getKey()){
                m.toggle();
            }
        }
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getToggleModules() {
        ArrayList<Module> togglelist = new ArrayList<Module>();
        for(Module m : getModules()){
            if(m.isToggle()){
                togglelist.add(m);
            }
        }
        return togglelist;
    }


    public static Module getModuleByName(String name) {
        for (Module m : modules) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }
}
