package xyz.Dot.custom;

import net.minecraft.client.Minecraft;
import xyz.Dot.custom.base.Component;
import xyz.Dot.custom.base.TextComponent;
import xyz.Dot.custom.components.*;
import xyz.Dot.ui.Custom;

import java.util.ArrayList;

public class ComponentManager {
    public final ArrayList<Component> components = new ArrayList<>();
    public boolean init = false;

    public Component dragging;

    public ComponentManager(){
        init();
    }

    public void init() {
        components.add(new Watermark());
        components.add(new BPSGraph());
        components.add(new KeyStrokes());
        components.add(new PotionEffects());
        components.add(new BetterScoreboard());
        components.add(new Armors());
        components.add(new Coordinate());
        components.add(new TextComponent("FPS", () -> Minecraft.getDebugFPS() + " FPS"));
        components.add(new TextComponent("CPS", () -> KeyStrokes.lastCPS + " CPS"));
        components.add(new TextComponent("ServerAddress",() -> Minecraft.getMinecraft().isIntegratedServerRunning() ? "Singleplayer" : Minecraft.getMinecraft().getCurrentServerData().serverIP));
        components.add(new TextComponent("ItemInfo", () -> {
            if (Minecraft.getMinecraft().currentScreen instanceof Custom) {
                return "ItemInfo";
            } else {
                if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                    return Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName() + " x" + Minecraft.getMinecraft().thePlayer.getHeldItem().stackSize;
                }
            }
            return "";
        }));
    }

    public void drawComponents () {
        for (Component component : components) {
            component.draw(Minecraft.getMinecraft().timer.renderPartialTicks);
        }
    }
}
