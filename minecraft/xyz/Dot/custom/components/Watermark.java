package xyz.Dot.custom.components;

import net.minecraft.client.Minecraft;
import xyz.Dot.Client;
import xyz.Dot.custom.Component;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;

public class Watermark extends Component {
    public Watermark() {
        super(64, 56, "Watermark");
    }

    @Override
    public void drawHUD(float x, float y, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        String CName = Client.instance.client_name;

        int StartX = (int) x;
        int StartY = (int) y;
        int finalStartY = StartY;
        int finalStartY1 = StartY;
        ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(StartX, finalStartY, StartX + 64, finalStartY1 + 56,4, new Color(0, 0, 0, 64)));
        RenderUtils.drawRoundRect(StartX, finalStartY, StartX + 64, finalStartY1 + 56,4, new Color(0, 0, 0, 64));

        RenderUtils.drawHalfRoundRect(StartX, StartY, StartX + 64, StartY + 12, 4,  HUD.transparent.isToggle() ? new Color(0,0,0,100) : CustomColor.getColor());

        RenderUtils.drawFilledCircle(StartX + 6, StartY + 6, 3, new Color(255, 0, 0, 32));
        RenderUtils.drawFilledCircle(StartX + 7, StartY + 5, 3, new Color(255, 0, 0, 32));
        RenderUtils.drawFilledCircle(StartX + 7, StartY + 7, 3, new Color(255, 0, 0, 32));

        RenderUtils.drawFilledCircle(StartX + 10, StartY + 6, 3, new Color(0, 0, 255, 32));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 5, 3, new Color(0, 0, 255, 32));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 7, 3, new Color(0, 0, 255, 32));

        RenderUtils.drawFilledCircle(StartX + 7, StartY + 6, 3, new Color(255, 0, 0, 128));
        // RenderUtils.drawFilledCircle(StartX + 8, StartY + 6, 3, new Color(0, 255,
        // 0,128));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 6, 3, new Color(0, 0, 255, 128));

        try {
            FontLoaders.normalfont16.drawString(CName, StartX + 14, StartY + 4, new Color(255, 255, 255).getRGB());
            StartY += 20;
            FontLoaders.normalfont16.drawString( Translator.getInstance().m("FPS") + ": " + mc.getDebugFPS(), StartX + 5, StartY, new Color(255, 255, 255).getRGB());
            StartY += 12;
            String ping = String.valueOf(mc.getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime());
            if (ping.equals("0")) {
                ping = Translator.getInstance().m("Failed");
            }
            FontLoaders.normalfont16.drawString(Translator.getInstance().m("PING") + ": " + ping, StartX + 5, StartY, new Color(255, 255, 255).getRGB());
            StartY += 12;
            FontLoaders.normalfont16.drawString(Translator.getInstance().m("BPS") + ": " + HUD.movespeed, StartX + 5, StartY, new Color(255, 255, 255).getRGB());
        }catch (Exception e){

        }
    }
}
