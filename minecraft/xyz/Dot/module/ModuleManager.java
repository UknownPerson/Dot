package xyz.Dot.module;

import xyz.Dot.module.Movement.Sprint;
import xyz.Dot.event.EventBus;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.misc.EventKey;
import xyz.Dot.module.Render.HUD;

import java.util.ArrayList;

public class ModuleManager {
    static ArrayList<Module> modules;

    public ModuleManager(){
        modules = new ArrayList();
        EventBus.getInstance().register(this);
    }

    public void loadModule(){
        //combat

        //movement
        this.addModule(new Sprint());
        this.addModule(new Sprint());
        this.addModule(new Sprint());
        this.addModule(new Sprint());
        this.addModule(new Sprint());
        this.addModule(new Sprint());
        this.addModule(new Sprint());

        //player

        //render
        this.addModule(new HUD());

        //world
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
