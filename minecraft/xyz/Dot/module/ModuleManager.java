package xyz.Dot.module;

import xyz.Dot.module.Render.NameTag;
import xyz.Dot.module.Cheat.ViewClip;
import xyz.Dot.module.Client.ClickGui;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.module.Misc.KeyBoard;
import xyz.Dot.module.Misc.Sprint;
import xyz.Dot.event.EventBus;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.misc.EventKey;
import xyz.Dot.module.Cheat.NoJumpDelay;
import xyz.Dot.module.Render.*;

import java.util.ArrayList;

public class ModuleManager {
    static ArrayList<Module> modules;

    public ModuleManager(){
        modules = new ArrayList();
        EventBus.getInstance().register(this);
    }

    public void loadModule(){
        //Cheat
        this.addModule(new NoJumpDelay());
        this.addModule(new ViewClip());

        //Client
        this.addModule(new ClickGui());
        this.addModule(new HUD());

        //Misc
        this.addModule(new KeyBoard());
        this.addModule(new Sprint());

        //Render
        this.addModule(new EveryThingBlock());
        this.addModule(new FullBright());
        this.addModule(new NameTag());
        this.addModule(new NoHurtCamera());
        this.addModule(new OldBlockRender());
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
