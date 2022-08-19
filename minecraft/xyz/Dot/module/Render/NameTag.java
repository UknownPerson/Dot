package xyz.Dot.module.Render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.utils.RenderUtils;

public class NameTag extends Module {

    public NameTag() {
        super("NameTag", Keyboard.KEY_NONE, Category.Render);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {

    }
}
