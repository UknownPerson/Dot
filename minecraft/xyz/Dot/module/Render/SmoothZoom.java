package xyz.Dot.module.Render;

import net.minecraft.client.renderer.EntityRenderer;
import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.utils.RenderUtils;

public class SmoothZoom extends Module {
    public SmoothZoom() {
        super("SmoothZoom", Keyboard.KEY_NONE, Category.Render);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        EntityRenderer.ftemp = RenderUtils.toanim(EntityRenderer.ftemp, EntityRenderer.fto, 8, 0.01f);
    }
}
