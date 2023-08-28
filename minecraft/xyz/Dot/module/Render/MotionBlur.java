package xyz.Dot.module.Render;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.world.EventFrame;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;

public class MotionBlur extends Module {

    public static Setting amount = new Setting(Client.instance.getModuleManager().getModuleByName("MotionBlur"), "Amount", .5d, .05, 1d, .01d);
    public MotionBlur() {
        super("MotionBlur", Keyboard.KEY_NONE, Category.Render);
        addValues(amount);
    }


    @EventHandler
    public void onRenderFrame(EventFrame event) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        final float normalizeValue = (float) Math.min(amount.getCurrentValue(), .95F);
        GL11.glAccum(GL11.GL_MULT, normalizeValue);
        GL11.glAccum(GL11.GL_DEPTH_BUFFER_BIT, 1F - normalizeValue);
        GL11.glAccum(GL11.GL_RETURN, 1F);
    }
}
