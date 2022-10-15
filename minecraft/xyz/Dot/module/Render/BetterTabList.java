package xyz.Dot.module.Render;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.utils.RenderUtils;

public class BetterTabList extends Module {
    public BetterTabList() {
        super("BetterTabList", Keyboard.KEY_NONE, Category.Render);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if (GuiPlayerTabOverlay.yto > GuiPlayerTabOverlay.k1) {
            GuiPlayerTabOverlay.k1 = RenderUtils.toanim(GuiPlayerTabOverlay.k1, GuiPlayerTabOverlay.yto, 12, 1f);
        } else {
            GuiPlayerTabOverlay.k1 = RenderUtils.toanim1(GuiPlayerTabOverlay.k1, 0, GuiPlayerTabOverlay.yto, 12, 2f);
        }
    }
}
