package xyz.Dot.module;

import xyz.Dot.module.Movement.Sprint;
import xyz.Dot.event.EventBus;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.misc.EventKey;
import xyz.Dot.module.Player.NoJumpDelay;
import xyz.Dot.module.Render.*;

import java.util.ArrayList;

public class ModuleManager {
    static ArrayList<Module> modules;

    public ModuleManager(){
        modules = new ArrayList();
        EventBus.getInstance().register(this);
    }

    public void loadModule(){
        //movement
        this.addModule(new Sprint());

        //player
        this.addModule(new NoJumpDelay());

        //render
        this.addModule(new ClickGui());

        this.addModule(new EveryThingBlock());

        this.addModule(new FullBright());

        this.addModule(new HUD());

        this.addModule(new OldBlockRender());

        this.addModule(new ViewClip());
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


    public static Module getModuleByName(String name) {
        for (Module m : modules) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }
}
