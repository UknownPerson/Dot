package xyz.Dot.module.Misc;

import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.utils.RenderUtils;

public class BetterSneak extends Module {
    public BetterSneak() {
        super("BetterSneak", Keyboard.KEY_NONE, Category.Misc);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        EntityPlayer.sneakanim = RenderUtils.toanim(EntityPlayer.sneakanim, EntityPlayer.fto, 12, 0.001f);
    }
}
