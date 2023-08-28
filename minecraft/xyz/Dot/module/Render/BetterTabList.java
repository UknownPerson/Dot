package xyz.Dot.module.Render;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.RenderUtils;

public class BetterTabList extends Module {
    public static Setting header = new Setting(Client.instance.getModuleManager().getModuleByName("BetterTabList"), "Header", false);
    public static Setting footer = new Setting(Client.instance.getModuleManager().getModuleByName("BetterTabList"), "Footer", false);
    public BetterTabList() {
        super("BetterTabList", Keyboard.KEY_NONE, Category.Render);
        this.addValues(header,footer);
    }
    @EventHandler
    public void renderHud(EventRender2D event) {
        if (GuiPlayerTabOverlay.yto > GuiPlayerTabOverlay.k1) {
            GuiPlayerTabOverlay.k1 = RenderUtils.toanim(GuiPlayerTabOverlay.k1, GuiPlayerTabOverlay.yto, 12, 1f);
        } else {
            GuiPlayerTabOverlay.k1 = RenderUtils.toanim(GuiPlayerTabOverlay.k1, GuiPlayerTabOverlay.yto, 12, 1f);
        }
    }
}
