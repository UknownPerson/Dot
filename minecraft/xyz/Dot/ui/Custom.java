package xyz.Dot.ui;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import xyz.Dot.Client;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class Custom extends GuiScreen {

    static CFontRenderer font = FontLoaders.normalfont16;
    static CFontRenderer font1 = FontLoaders.normalfont10;
    static Minecraft mc = Minecraft.getMinecraft();
    static int dotstartx = 20;
    static int dotstarty = 25;
    static int bpsavgstartx = 20;
    static int bpsavgstarty = 96;

    boolean keydown = false;
    int check = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // if()
    }

    public static void drawDot() {
        Minecraft mc = Minecraft.getMinecraft();
        String CName = Client.instance.client_name;

        int StartX = dotstartx;
        int StartY = dotstarty;
        RenderUtils.drawRect(StartX, StartY + 12, StartX + 64, StartY + 56, new Color(0, 0, 0, 64).getRGB());
        RenderUtils.drawRect(StartX, StartY, StartX + 64, StartY + 12, new Color(64, 128, 255, 200).getRGB());

        RenderUtils.drawFilledCircle(StartX + 6, StartY + 6, 3, new Color(255, 0, 0, 32));
        RenderUtils.drawFilledCircle(StartX + 7, StartY + 5, 3, new Color(255, 0, 0, 32));
        RenderUtils.drawFilledCircle(StartX + 7, StartY + 7, 3, new Color(255, 0, 0, 32));

        RenderUtils.drawFilledCircle(StartX + 10, StartY + 6, 3, new Color(0, 0, 255, 32));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 5, 3, new Color(0, 0, 255, 32));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 7, 3, new Color(0, 0, 255, 32));

        RenderUtils.drawFilledCircle(StartX + 7, StartY + 6, 3, new Color(255, 0, 0, 128));
        //RenderUtils.drawFilledCircle(StartX + 8, StartY + 6, 3, new Color(0, 255, 0,128));
        RenderUtils.drawFilledCircle(StartX + 9, StartY + 6, 3, new Color(0, 0, 255, 128));

        font.drawString(CName, StartX + 14, StartY + 4, new Color(255, 255, 255).getRGB());
        StartY += 20;
        font.drawString("FPS: " + mc.getDebugFPS(), StartX + 5, StartY, new Color(255, 255, 255).getRGB());
        StartY += 12;
        String ping = String.valueOf(mc.getNetHandler().getPlayerInfo(Minecraft.thePlayer.getUniqueID()).getResponseTime());
        if (ping.equals("0")) {
            ping = "Failed";
        }
        font.drawString("PING: " + ping, StartX + 5, StartY, new Color(255, 255, 255).getRGB());
        StartY += 12;
        font.drawString("BPS: " + HUD.movespeed, StartX + 5, StartY, new Color(255, 255, 255).getRGB());

    }

    public static void drawBPSAVG() {
        int StartXspeed = bpsavgstartx;
        int StartYspeed = bpsavgstarty;
        RenderUtils.drawRect(StartXspeed, StartYspeed + 12, StartXspeed + 96, StartYspeed + 62, new Color(0, 0, 0, 64).getRGB());
        RenderUtils.drawRect(StartXspeed, StartYspeed, StartXspeed + 96, StartYspeed + 12, new Color(64, 128, 255, 200).getRGB());
        int numsm = HUD.nums - 1;
        float xnum = 0.5f;
        float[] avglist = new float[100];
        int avgnum = 0;
        for (int i = 0; i <= 95; i++) {
            int rank = numsm - i;
            if (rank < 1) {
                rank += 100;
            }
            float mspeed = HUD.bps[rank];
            while ((mspeed / xnum) > 50) {
                xnum += 0.5f;
            }

            avglist[avgnum] = mspeed;
            avgnum++;
        }

        String maxstring = String.valueOf((int) (xnum * 50));
        font1.drawString(maxstring, StartXspeed + 96 - font1.getStringWidth(maxstring) - 2, StartYspeed + 16, new Color(0, 0, 0, 128).getRGB());

        for (int i = 0; i <= 95; i++) {
            int rank = numsm - i;
            if (rank < 1) {
                rank += 100;
            }
            float mspeed = HUD.bps[rank];

            RenderUtils.drawRect(StartXspeed + 95 - i, (int) (StartYspeed + (62 - (mspeed / xnum))), StartXspeed + 96 - i, StartYspeed + 62, new Color(255, 255, 255, 128).getRGB());
        }

        float avg = 0;
        for (float x : avglist) {
            avg += x;
        }
        avg = Math.round((avg / 96) * 100) / 100.0f;

        String mtext = "BPS.AVG: " + avg;
        font.drawString(mtext, StartXspeed + 5, StartYspeed + 4, new Color(255, 255, 255).getRGB());
        int num = 0;
    }

    public static void drawScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(objective);
        List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>() {
            public boolean apply(Score p_apply_1_) {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));

        if (list.size() > 15) {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        } else {
            collection = list;
        }

        int i = mc.fontRendererObj.getStringWidth(objective.getDisplayName());

        for (Score score : collection) {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s;
            if (false) {
                s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            } else {
                s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": ";
            }
            i = Math.max(i, mc.fontRendererObj.getStringWidth(s));
        }

        int i1 = collection.size() * mc.fontRendererObj.FONT_HEIGHT;
        //fuckyou dont forget
        int j1 = scaledRes.getScaledHeight() / 2 + i1 / 3;
        int k1 = 3;

        j1 = scaledRes.getScaledHeight() - 16;
        k1 = 16;

        int l1 = scaledRes.getScaledWidth() - i - k1;
        int j = 0;

        for (Score score1 : collection) {
            ++j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            String s2;
            if (false) {
                s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
            } else {
                s2 = "";
            }

            int k = j1 - j * mc.fontRendererObj.FONT_HEIGHT;
            int l = scaledRes.getScaledWidth() - k1 + 2;
            RenderUtils.drawRect(l1 - 2, k, l, k + mc.fontRendererObj.FONT_HEIGHT, new Color(0, 0, 0, 64).getRGB());
            mc.fontRendererObj.drawString(s1, l1, k, 553648127);
            if (false) {
                mc.fontRendererObj.drawString(s2, l - mc.fontRendererObj.getStringWidth(s2), k, 553648127);
            }

            if (j == collection.size()) {
                String s3 = objective.getDisplayName() + 10;
                RenderUtils.drawRect(l1 - 2, k - 13, l, k - 1, new Color(64, 128, 255, 200).getRGB());
                RenderUtils.drawRect(l1 - 2, k - 1, l, k, 1342177280);
                mc.fontRendererObj.drawString(s3, (l1 - 2) + 5, k - mc.fontRendererObj.FONT_HEIGHT - 2, 553648127);
            }
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

}
