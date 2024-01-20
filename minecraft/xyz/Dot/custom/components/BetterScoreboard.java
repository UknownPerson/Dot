package xyz.Dot.custom.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import xyz.Dot.Client;
import xyz.Dot.custom.base.Component;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Custom;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.shader.BloomUtil;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;

public class BetterScoreboard extends Component {

    public static ScoreObjective objective;

    private static Framebuffer bloomFramebuffer = new Framebuffer(1, 1, false);
    private final Setting num = new Setting(this.module, "Number", false);
    public static Setting blurradius = new Setting(Client.instance.getModuleManager().getModuleByName("BetterScoreboard"), "BlurRadius", 8.0d, 1d, 128.0d, 0.1d);
    public float blurrtest = 1;

    private final Setting blur = new Setting(this.module, "BlurTest", false);

    public BetterScoreboard() {
        super(96, 12, "BetterScoreboard");
        this.addValues(blur, num);
    }

    @Override
    public void drawHUD(float x, float y, float partialTicks) {
        int sX = (int) x;
        int sY = (int) y;

        if (objective == null)
            return;

        final net.minecraft.scoreboard.Scoreboard scoreboard = objective.getScoreboard();
        final List<Score> list = scoreboard.getSortedScores(objective).stream()
                .filter(p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#"))
                .limit(15)
                .collect(Collectors.toList());

        final int length = list.size();

        final FontRenderer fontRenderer = mc.fontRendererObj;

        double width = fontRenderer.getStringWidth(objective.getDisplayName()) + 4;

        final String[] formattedPlayerNames = new String[length];

        for (int i = 0; i < length; i++) {
            final Score score = list.get(i);

            final ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            final String playerName = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName());
            formattedPlayerNames[i] = playerName;
            if (num.isToggle()) {
                formattedPlayerNames[i] = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + "    \r" + score.getScorePoints();
            }
            width = Math.max(width, fontRenderer.getStringWidth(formattedPlayerNames[i]) + 4);
        }

        final int height = 11 + list.size() * mc.fontRendererObj.FONT_HEIGHT;
        double finalWidth = width;
        if (HUD.blur.isToggle()) {
            Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
            GuiIngame.checkSetupFBO(Minecraft.getMinecraft().getFramebuffer());
            glClear(GL_STENCIL_BUFFER_BIT);
            glEnable(GL_STENCIL_TEST);

            glStencilFunc(GL_ALWAYS, 1, 1);
            glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
            glColorMask(false, false, false, false);
            if (Minecraft.getMinecraft().currentScreen instanceof Custom) {
                RenderUtils.drawRoundRect(sX, sY, (int) (sX + width), sY + height + 16, 4, new Color(0, 0, 0, 64));
            } else {
                RenderUtils.drawRoundRect(sX - 1, sY + 9, (int) (sX + width + 1), sY + height + 17, 5, new Color(255, 255, 255, 32));
                //RenderUtils.drawRoundRect(sX, sY + 10, (int) (sX + width), sY + height + 16,4, new Color(255, 255, 255, 32));
                RenderUtils.drawRoundRect(sX, sY + 10, (int) (sX + width), sY + height + 16, 4, new Color(0, 0, 0, 64));
            }

            glColorMask(true, true, true, true);
            glStencilFunc(GL_EQUAL, 1, 1);
            glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
            float br = (float) blurradius.getCurrentValue();
            if(blur.isToggle()){
                if (HUD.nowspeed != 0 || HUD.avgspeed != 0) {
                    float d = HUD.nowspeed + 1;
                    d = Math.max(1f,d);
                    br = d * br;
                    br = Math.min(128f,br);
                    blurrtest = RenderUtils.toanim(blurrtest,br,8,1f);
                }
            }else{
                blurrtest = br;
            }

            ShaderManager.renderBlur(blurrtest);
            glDisable(GL_STENCIL_TEST);
        }
        if (HUD.shadow.isToggle()) {
            bloomFramebuffer = ShaderManager.createFrameBuffer(bloomFramebuffer);
            bloomFramebuffer.framebufferClear();
            bloomFramebuffer.bindFramebuffer(true);

            if (Minecraft.getMinecraft().currentScreen instanceof Custom) {
                RenderUtils.drawRoundRect(sX, sY, (int) (sX + width), sY + height + 16, 4, new Color(0, 0, 0, 64));
            } else {
                RenderUtils.drawRoundRect(sX - 1, sY + 9, (int) (sX + width + 1), sY + height + 17, 5, new Color(255, 255, 255, 32));
                //RenderUtils.drawRoundRect(sX, sY + 10, (int) (sX + width), sY + height + 16,4, new Color(255, 255, 255, 32));
                RenderUtils.drawRoundRect(sX, sY + 10, (int) (sX + width), sY + height + 16, 4, new Color(0, 0, 0, 64));
            }

            bloomFramebuffer.unbindFramebuffer();
            BloomUtil.renderBlur(bloomFramebuffer.framebufferTexture, 10, 2);
        }


        if (Minecraft.getMinecraft().currentScreen instanceof Custom) {
            RenderUtils.drawRoundRect(sX, sY, (int) (sX + width), sY + height + 16, 4, new Color(0, 0, 0, 64));
            RenderUtils.drawHalfRoundRect(sX, sY, (int) (sX + width), sY + 12, 4, HUD.transparent.isToggle() ? new Color(0, 0, 0, 100) : CustomColor.getColor());
            FontLoaders.normalfont16.drawString(Translator.getInstance().m("Scoreboard"), sX + 5, sY + 4, new Color(255, 255, 255).getRGB());
        } else {
            RenderUtils.drawRoundRect(sX - 1, sY + 9, (int) (sX + width + 1), sY + height + 17, 5, new Color(255, 255, 255, 32));
            //RenderUtils.drawRoundRect(sX, sY + 10, (int) (sX + width), sY + height + 16,4, new Color(255, 255, 255, 32));
            RenderUtils.drawRoundRect(sX, sY + 10, (int) (sX + width), sY + height + 16, 4, new Color(0, 0, 0, 64));
        }


        final int sb = sY + 14;
        this.setWidth((int) (width));
        for (int i = 0; i < length; i++) {

            fontRenderer.drawStringWithShadow(processString(formattedPlayerNames[i].split("\r")[0]), (float) ((double) sX + 2), sb + height - (i + 1) * 9, 0xFFFFFFFF);
            if (num.isToggle()) {
                String num = EnumChatFormatting.RED + "" + formattedPlayerNames[i].split("\r")[1];
                Minecraft.getMinecraft().fontRendererObj.drawString(num, x + width - Minecraft.getMinecraft().fontRendererObj.getStringWidth(num), sb + height - (i + 1) * 9, -1);
            }
        }

        fontRenderer.drawStringWithShadow(processString(objective.getDisplayName()),
                (float) ((double) sX + width / 2.0F - fontRenderer.getStringWidth(processString(objective.getDisplayName())) / 2.0F),
                sb, 0xFFFFFFFF);
    }

    private String processString(String text) {
        String str = "";
        for (char c : text.toCharArray()) {
            if ((c < 50000 || c > 60000) && c != 9917) str += c;
        }
        text = str.replace("\247r", "").replace('\u25ac', '\u003d').replace('\u2764', '\u2665').replace('\u22c6', '\u2606').replace('\u2620', '\u2606').replace('\u2730', '\u2606').replace("\u272b", "\u2606").replace("\u2718", "\u002b");
        text = text.replace('\u2b05', '\u2190').replace('\u2b06', '\u2191').replace('\u2b07', '\u2193').replace('\u27a1', '\u2192').replace('\u2b08', '\u2197').replace('\u2b0b', '\u2199').replace('\u2b09', '\u2196').replace('\u2b0a', '\u2198');
        return text;
    }
}
