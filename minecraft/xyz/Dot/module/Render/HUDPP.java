package xyz.Dot.module.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;

public class HUDPP extends Module {

    public HUDPP() {
        super("HUD++", Keyboard.KEY_NONE, Category.Render);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        int y = 0;
        for (PotionEffect effect : Minecraft.getMinecraft().thePlayer.getActivePotionEffects()) {
            int ychat;
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName());
            switch (effect.getAmplifier()) {
                case 0: {
                    break;
                }
                case 1: {
                    PType = PType + " II";
                    break;
                }
                case 2: {
                    PType = PType + " III";
                    break;
                }
                case 3: {
                    PType = PType + " IV";
                    break;
                }
                default: {
                    PType = PType + effect.getAmplifier();
                }
            }
            int n = ychat = -10;
            float x1 = RenderUtils.width() - mc.fontRendererObj.getStringWidth(PType) - 2;
            float y1 = RenderUtils.height() - mc.fontRendererObj.FONT_HEIGHT + y - 12 - ychat - 20;
            RenderUtils.drawRect((int) x1, (int) y1, (int) (x1 + mc.fontRendererObj.getStringWidth(PType)), (int) (y1 + mc.fontRendererObj.FONT_HEIGHT + mc.fontRendererObj.FONT_HEIGHT), new Color(0, 0, 0, 128).getRGB());
            mc.fontRendererObj.drawStringWithShadow(PType, x1, y1, potion.getLiquidColor());
            mc.fontRendererObj.drawStringWithShadow(Potion.getDurationString(effect), x1, y1 + mc.fontRendererObj.FONT_HEIGHT, potion.getLiquidColor());
            y -= 24;
        }
    }
}