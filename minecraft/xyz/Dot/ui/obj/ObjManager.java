package xyz.Dot.ui.obj;

import org.newdawn.slick.util.Log;
import xyz.Dot.event.EventBus;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.ui.obj.objs.test;

import java.util.ArrayList;

public class ObjManager {
    static ArrayList<Obj> objs;

    public void loadObjs() {
        objs = new ArrayList<>();

        objs.add(new test());
        EventBus.getInstance().register(this);
    }

    @EventHandler
    public void onRender(EventRender2D e) {
        for (Obj o : objs) {
            o.doAnim();
            o.draw();
        }
    }
}
