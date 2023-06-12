package xyz.Dot.custom;

import net.minecraft.client.Minecraft;
import xyz.Dot.custom.components.BPSGraph;
import xyz.Dot.custom.components.BetterScoreboard;
import xyz.Dot.custom.components.KeyStrokes;
import xyz.Dot.custom.components.Watermark;

import java.util.ArrayList;

public class ComponentManager {
    public final ArrayList<Component> components = new ArrayList<>();
    public boolean init = false;

    public ComponentManager(){
        init();
    }

    public void init() {
        components.add(new Watermark());
        components.add(new BPSGraph());
        components.add(new KeyStrokes());
        components.add(new BetterScoreboard());
    }

    public void drawComponents () {
        for (Component component : components) {
            component.draw(Minecraft.getMinecraft().timer.renderPartialTicks);
        }
    }
}
