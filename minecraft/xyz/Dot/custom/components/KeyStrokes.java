package xyz.Dot.custom.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import xyz.Dot.custom.base.Component;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.Custom;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;
import java.util.ArrayList;

public class KeyStrokes extends Component {

    static float wc = 0f;
    static float sc = 0f;
    static float ac = 0f;
    static float dc = 0f;
    static float lc = 0f;
    static float rc = 0f;
    static float spacec = 0f;
    static float shiftc = 0f;
    public static ArrayList<Long> cpsl = new ArrayList<>();
    public static ArrayList<Long> cpsr = new ArrayList<>();
    public static boolean cpsldown;
    public static boolean cpsrdown;
    public static int lastCPS =0 ;

    public KeyStrokes() {
        super(75, 12, "KeyStrokes");
    }

    @Override
    public void drawHUD(float x, float y, float partialTicks) {
        int sX = (int)x;
        int sY = (int)y+12;
        int background = 0;
        int press = 255;
        CFontRenderer font = FontLoaders.normalfont16;
        boolean blur = HUD.blur.isToggle();
        Minecraft minecraft = Minecraft.getMinecraft();
        GameSettings gameSettings = minecraft.gameSettings;

        if(Minecraft.getMinecraft().currentScreen instanceof Custom)
        {
            RenderUtils.drawHalfRoundRect((int) x, (int) y, (int) x + 81, (int) y + 12, 4, HUD.transparent.isToggle() ? new Color(0,0,0,164) : CustomColor.getColor());
            RenderUtils.drawRect((int) x, (int) y, (int) x + 81, (int) y + 117, new Color(0, 0, 0, 64).getRGB());
            font.drawString(Translator.getInstance().m("KeyStrokes"), (int) x + 5, (int) y + 4, new Color(255, 255, 255).getRGB());
        }

        int multiplier = 6;
        CFontRenderer font1 = FontLoaders.normalfont10;
        wc = RenderUtils.toanim(wc, gameSettings.keyBindLeft.isKeyDown() ? press : background, multiplier, 0.1f);
        sc = RenderUtils.toanim(sc, gameSettings.keyBindRight.isKeyDown() ? press : background, multiplier, 0.1f);
        ac = RenderUtils.toanim(ac, gameSettings.keyBindBack.isKeyDown() ? press : background, multiplier, 0.1f);
        dc = RenderUtils.toanim(dc, gameSettings.keyBindJump.isKeyDown() ? press : background, multiplier, 0.1f);
        lc = RenderUtils.toanim(lc, gameSettings.keyBindPickBlock.isKeyDown() ? press : background, multiplier, 0.1f);
        rc = RenderUtils.toanim(rc, gameSettings.keyBindDrop.isKeyDown() ? press : background, multiplier, 0.1f);
        spacec = RenderUtils.toanim(spacec, gameSettings.keyBindSneak.isKeyDown() ? press : background, multiplier, 0.1f);
        shiftc = RenderUtils.toanim(shiftc, gameSettings.keyBindSprint.isKeyDown() ? press : background, multiplier, 0.1f);

        String s;
        s = "W";
        int finalY = sY;
        int finalY1 = sY;
        int round = 2;
        if(blur){
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(sX + 28, finalY, sX + 53, finalY1 + 25, round, new Color((int) wc, (int) wc, (int) wc, 128)));
        }
        int finalY2 = sY;
        int finalY5 = sY;
        if(blur) {
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(sX + 28, finalY2, sX + 53, finalY5 + 25, round, new Color((int) wc, (int) wc, (int) wc, 128)));
        }
        RenderUtils.drawRoundRect(sX + 28, sY, sX + 53, sY + 25, round, new Color((int) wc, (int) wc, (int) wc, 128));
        font.drawCenteredString(s, sX + 28 + 25 / 2f, sY + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "S";
        int finalY3 = sY;
        if(blur) {
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(sX + 28, finalY3 + 28, sX + 53, finalY3 + 53, round, new Color((int) sc, (int) sc, (int) sc, 128)));

            int finalY4 = sY;
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(sX + 28, finalY4 + 28, sX + 53, finalY4 + 53, round, new Color((int) sc, (int) sc, (int) sc, 128)));
        }
        RenderUtils.drawRoundRect(sX + 28, sY + 28, sX + 53, sY + 53, round, new Color((int) sc, (int) sc, (int) sc, 128));
        font.drawString(s, sX + 28 + (25 - font.getStringWidth(s)) / 2, sY + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "A";
        int finalY6 = sY;
        if(blur) {
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(sX, finalY6 + 28, sX + 25, finalY6 + 53, round, new Color((int) ac, (int) ac, (int) ac, 128)));
            int finalY7 = sY;
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(sX, finalY7 + 28, sX + 25, finalY7 + 53, round, new Color((int) ac, (int) ac, (int) ac, 128)));
        }
        RenderUtils.drawRoundRect(sX, sY + 28, sX + 25, sY + 53, round, new Color((int) ac, (int) ac, (int) ac, 128));
        font.drawCenteredString(s, sX + 25 / 2f, sY + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "D";
        int finalY8 = sY;
        if(blur) {
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(sX + 56, finalY8 + 28, sX + 81, finalY8 + 53, round, new Color((int) dc, (int) dc, (int) dc, 128)));
            int finalY9 = sY;
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(sX + 56, finalY9 + 28, sX + 81, finalY9 + 53, round, new Color((int) dc, (int) dc, (int) dc, 128)));
        }
        RenderUtils.drawRoundRect(sX + 56, sY + 28, sX + 81, sY + 53, round, new Color((int) dc, (int) dc, (int) dc, 128));
        font.drawCenteredString(s, sX + 56 + 25 / 2f, sY + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        sY += 56;
        s = "LMB";
        int finalY10 = sY;
        int finalY11 = sY;
        if(blur) {
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(sX, finalY10, sX + (81 - 3) / 2, finalY11 + 25, round, new Color((int) lc, (int) lc, (int) lc, 128)));
            int finalY12 = sY;
            int finalY13 = sY;
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(sX, finalY12, sX + (81 - 3) / 2, finalY13 + 25, round, new Color((int) lc, (int) lc, (int) lc, 128)));
        }
        RenderUtils.drawRoundRect(sX, sY, sX + (81 - 3) / 2, sY + 25, round, new Color((int) lc, (int) lc, (int) lc, 128));
        font.drawCenteredString(s, sX + (81 - 3) / 2 / 2f, sY + (20 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());
        int count = 0;
        int f =0;
        for (long i : cpsl) {
            long thistime = System.nanoTime();
            if ((thistime - i) < 1000000000) {
                count += 1;
            }
        }
        if (count == 0) {
            cpsl.clear();
        }
        lastCPS = count;
        s = String.valueOf(count);
        font1.drawCenteredString(s, sX + (81 - 3) / 2 / 2f, sY + 16 + (5 - font1.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "RMB";
        int finalY14 = sY;
        if(blur) {
            int finalY15 = sY;
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(sX + (81 + 3) / 2, finalY14, sX + 81, finalY15 + 25, round, new Color((int) rc, (int) rc, (int) rc, 128)));
            int finalY16 = sY;
            int finalY17 = sY;
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(sX + (81 + 3) / 2, finalY16, sX + 81, finalY17 + 25, round, new Color((int) rc, (int) rc, (int) rc, 128)));
        }
        RenderUtils.drawRoundRect(sX + (81 + 3) / 2, sY, sX + 81, sY + 25, round, new Color((int) rc, (int) rc, (int) rc, 128));
        font.drawString(s, sX + ((81 + 3) / 2) + ((81 - ((81 + 3) / 2)) - font.getStringWidth(s)) / 2 + 1, sY + (20 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());
        int count1 = 0;
        for (long i : cpsr) {
            long thistime = System.nanoTime();
            if ((thistime - i) < 1000000000) {
                count1 += 1;
            }
            if (count == 0) {
                cpsl.clear();
            }
        }
        s = String.valueOf(count1);
        font1.drawString(s, sX + ((81 + 3) / 2) + ((81 - ((81 + 3) / 2)) - font.getStringWidth(s)) / 2 + 1, sY + 16 + (5 - font1.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        sY += 28;
        s = "\u00a7m\u00a7l--------";
        int finalY18 = sY;
        int finalY19 = sY;
        if(blur) {
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(sX, finalY18, sX + 81, finalY19 + 15, round, new Color((int) spacec, (int) spacec, (int) spacec, 128)));
            int finalY20 = sY;
            int finalY21 = sY;
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(sX, finalY20, sX + 81, finalY21 + 15, round, new Color((int) spacec, (int) spacec, (int) spacec, 128)));
        }
        RenderUtils.drawRoundRect(sX, sY, sX + 81, sY + 15, round, new Color((int) spacec, (int) spacec, (int) spacec, 128));
        font.drawCenteredString(s, sX + 81 / 2f, sY + (15 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        sY += 18;
        s = "Sneak";
        int finalY22 = sY;
        int finalY23 = sY;
        if(blur) {
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(sX, finalY22, sX + 81, finalY23 + 15, round, new Color((int) shiftc, (int) shiftc, (int) shiftc, 128)));
            int finalY24 = sY;
            int finalY25 = sY;
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(sX, finalY24, sX + 81, finalY25 + 15, round, new Color((int) shiftc, (int) shiftc, (int) shiftc, 128)));
        }
        RenderUtils.drawRoundRect(sX, sY, sX + 81, sY + 15, round, new Color((int) shiftc, (int) shiftc, (int) shiftc, 128));
        font.drawString(s, sX + (81 - font.getStringWidth(s)) / 2, sY + (15 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

    }
}
