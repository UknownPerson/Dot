package xyz.Dot.custom.components;

import net.minecraft.util.BlockPos;
import xyz.Dot.custom.base.Component;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;

public class Coordinate extends Component {
    public Coordinate() {
        super(64, 56, "Coordinate");
    }

    @Override
    public void drawHUD(float x, float y, float partialTicks) {
        int StartX = (int) x;
        int StartY = (int) y;
        int finalStartY = StartY;
        int finalStartY1 = StartY;
        ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(StartX, finalStartY, StartX + width, finalStartY1 + height,4, new Color(0, 0, 0, 64)));
        RenderUtils.drawRoundRect(StartX, finalStartY, StartX + width, finalStartY1 + height,4, new Color(0, 0, 0, 64));
        RenderUtils.drawHalfRoundRect(StartX, StartY, StartX + width, StartY + 12, 4,  HUD.transparent.isToggle() ? new Color(0,0,0,100) : CustomColor.getColor());
        FontLoaders.normalfont16.drawString(Translator.getInstance().m("Coordinate"), (int) x + 5, (int) y + 4, new Color(255, 255, 255).getRGB());
        y = y + 12;
        String Biome = mc.theWorld.getBiomeGenForCoords(mc.thePlayer.getPosition()).biomeName;
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT * 4 + 24);
        this.setWidth(mc.fontRendererObj.getStringWidth(Translator.getInstance().m("Biome") + ": " + Biome + "SEEEEE"));
        mc.fontRendererObj.drawStringWithShadow("X: " + mc.thePlayer.getPosition().getX(), x + 5, y + 4, -1);
        mc.fontRendererObj.drawStringWithShadow("Y: " + mc.thePlayer.getPosition().getY(), x + 5, y + 4 + mc.fontRendererObj.FONT_HEIGHT + 2, -1);
        mc.fontRendererObj.drawStringWithShadow("Z: " + mc.thePlayer.getPosition().getZ(), x + 5, y + 4 + mc.fontRendererObj.FONT_HEIGHT + 2 + mc.fontRendererObj.FONT_HEIGHT + 2,-1);
        mc.fontRendererObj.drawStringWithShadow(Translator.getInstance().m("Biome") + ": ", x + 5, y + 4 + mc.fontRendererObj.FONT_HEIGHT + 2 + mc.fontRendererObj.FONT_HEIGHT + 2 + mc.fontRendererObj.FONT_HEIGHT + 2, -1);
        mc.fontRendererObj.drawStringWithShadow(Biome, x + 5 + mc.fontRendererObj.getStringWidth(Translator.getInstance().m("Biome") +": "), y + 4 + mc.fontRendererObj.FONT_HEIGHT + 2 + mc.fontRendererObj.FONT_HEIGHT + 2 + mc.fontRendererObj.FONT_HEIGHT + 2, mc.theWorld.getBiomeGenForCoords((BlockPos)mc.thePlayer.getPosition()).color);
    }


}
