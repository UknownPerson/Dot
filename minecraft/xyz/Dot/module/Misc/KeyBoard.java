package xyz.Dot.module.Misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;

public class KeyBoard extends Module {
    public KeyBoard() {
        super("KeyBoard", Keyboard.KEY_NONE, Category.Misc);
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        int x = 20;
        int y =200;
        int background = new Color(0, 0, 0, 100).getRGB();
        int press = new Color(255, 255, 255, 190).getRGB();

        Minecraft minecraft = Minecraft.getMinecraft();
        GameSettings gameSettings = minecraft.gameSettings;
        FontRenderer fontRendererObj = minecraft.fontRendererObj;

        RenderUtils.drawRect(x + 25, y, x + 50, y + 25, gameSettings.keyBindLeft.isKeyDown() ? press : background);
        fontRendererObj.drawString("W", x + 25 + 10, y + 10, 0xFFFFFFFF);
        RenderUtils.drawRect(x + 25, y + 25, x + 50, y + 50, gameSettings.keyBindRight.isKeyDown() ? press : background);
        fontRendererObj.drawString("S", x + 25 + 10, y + 25 + 10, 0xFFFFFFFF);
        RenderUtils.drawRect(x, y + 25, x + 25, y +50, gameSettings.keyBindBack.isKeyDown() ? press : background);
        fontRendererObj.drawString("A", x + 10, y + 25 + 10, 0xFFFFFFFF);
        RenderUtils.drawRect(x + 50, y + 25, x + 75,  y + 50, gameSettings.keyBindJump.isKeyDown() ? press : background);
        fontRendererObj.drawString("D", x + 50 + 10, y + 25 + 10, 0xFFFFFFFF);
    }
}
