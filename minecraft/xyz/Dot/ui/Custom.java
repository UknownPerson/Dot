package xyz.Dot.ui;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;
import xyz.Dot.Client;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
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
    static int scoreboardx;
    static int scoreboardx1;
    static int scoreboardy;
    static boolean first = false;
    boolean keydown = false;
    int check = 0;
    static int thisj = 0;
    int keydownX, keydownY;
    static int keystrokesx = 20, keystrokesy = 200;
    static ArrayList<Long> cpsl = new ArrayList<>();
    static ArrayList<Long> cpsr = new ArrayList<>();
    static boolean cpsldown;
    static boolean cpsrdown;
    public static boolean open = false;

    public static float[] fucktest = new float[12800];

    public static void drawKeyStrokes() {
        int x = keystrokesx;
        int y = keystrokesy;
        int background = new Color(0, 0, 0, 100).getRGB();
        int press = new Color(255, 255, 255, 190).getRGB();

        Minecraft minecraft = Minecraft.getMinecraft();
        GameSettings gameSettings = minecraft.gameSettings;

        String s;
        s = "W";
        RenderUtils.drawRect(x + 28, y, x + 53, y + 25, gameSettings.keyBindLeft.isKeyDown() ? press : background);
        font.drawString(s, x + 28 + (25 - font.getStringWidth(s)) / 2, y + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "S";
        RenderUtils.drawRect(x + 28, y + 28, x + 53, y + 53, gameSettings.keyBindRight.isKeyDown() ? press : background);
        font.drawString(s, x + 28 + (25 - font.getStringWidth(s)) / 2, y + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "A";
        RenderUtils.drawRect(x, y + 28, x + 25, y + 53, gameSettings.keyBindBack.isKeyDown() ? press : background);
        font.drawString(s, x + (25 - font.getStringWidth(s)) / 2, y + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "D";
        RenderUtils.drawRect(x + 56, y + 28, x + 81, y + 53, gameSettings.keyBindJump.isKeyDown() ? press : background);
        font.drawString(s, x + 56 + (25 - font.getStringWidth(s)) / 2, y + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        if (!gameSettings.keyBindPickBlock.isKeyDown()) {
            cpsldown = false;
        }
        if (gameSettings.keyBindPickBlock.isKeyDown() && !cpsldown) {
            cpsl.add(System.nanoTime());
            cpsldown = true;
        }

        if (!gameSettings.keyBindDrop.isKeyDown()) {
            cpsrdown = false;
        }
        if (gameSettings.keyBindDrop.isKeyDown() && !cpsrdown) {
            cpsr.add(System.nanoTime());
            cpsrdown = true;
        }

        y += 56;
        s = "LMB";
        RenderUtils.drawRect(x, y, x + (81 - 3) / 2, y + 25, gameSettings.keyBindPickBlock.isKeyDown() ? press : background);
        font.drawString(s, x + ((81 - 3) / 2 - font.getStringWidth(s)) / 2 + 1, y + (20 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());
        int count = 0;
        for (long i : cpsl) {
            long thistime = System.nanoTime();
            if ((thistime - i) < 1000000000) {
                count += 1;
            }
        }
        if (count == 0) {
            cpsl.clear();
        }
        s = String.valueOf(count);
        font1.drawString(s, x + ((81 - 3) / 2 - font.getStringWidth(s)) / 2 + 1, y + 16 + (5 - font1.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "RMB";
        RenderUtils.drawRect(x + (81 + 3) / 2, y, x + 81, y + 25, gameSettings.keyBindDrop.isKeyDown() ? press : background);
        font.drawString(s, x + ((81 + 3) / 2) + ((81 - ((81 + 3) / 2)) - font.getStringWidth(s)) / 2 + 1, y + (20 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());
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
        font1.drawString(s, x + ((81 + 3) / 2) + ((81 - ((81 + 3) / 2)) - font.getStringWidth(s)) / 2 + 1, y + 16 + (5 - font1.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        y += 28;
        s = "\u00a7m\u00a7l--------";
        RenderUtils.drawRect(x, y, x + 81, y + 15, gameSettings.keyBindSneak.isKeyDown() ? press : background);
        font.drawString(s, x + (81 - font.getStringWidth(s)) / 2 - 1, y + (15 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        y += 18;
        s = "Sneak";
        RenderUtils.drawRect(x, y, x + 81, y + 15, gameSettings.keyBindSprint.isKeyDown() ? press : background);
        font.drawString(s, x + (81 - font.getStringWidth(s)) / 2, y + (15 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

    }

    public static void ks() {
        RenderUtils.drawRect(keystrokesx, keystrokesy, keystrokesx + 81, keystrokesy - 12, new Color(64, 128, 255, 200).getRGB());
        RenderUtils.drawRect(keystrokesx, keystrokesy, keystrokesx + 81, keystrokesy + 117, new Color(0, 0, 0, 64).getRGB());
        font.drawString("KeyStrokes", keystrokesx + 5, keystrokesy - 12 + 4, new Color(255, 255, 255).getRGB());

    }

    public static void drawScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
        if (scoreboardx > RenderUtils.width()) {
            scoreboardx = RenderUtils.width();
        }
        if (scoreboardy > RenderUtils.height() - 9) {
            scoreboardy = RenderUtils.height() - 9;
        }

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

        int i = mc.fontRendererObj.getStringWidth(objective.getDisplayName()) + 10;

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
        if (first) {
            l1 = RenderUtils.width() - scoreboardx + 2;
            j1 = RenderUtils.height() - scoreboardy - j * mc.fontRendererObj.FONT_HEIGHT + 13;
        }
        int jtemp = 0;
        for (Score score1 : collection) {
            ++j;
            jtemp = j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            String s2;
            if (false) {
                s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
            } else {
                s2 = "";
            }

            int k = j1 - j * mc.fontRendererObj.FONT_HEIGHT;
            int l = RenderUtils.width() - scoreboardx + i + 2;
            RenderUtils.drawRect(l1 - 2, k, l, k + mc.fontRendererObj.FONT_HEIGHT, new Color(0, 0, 0, 64).getRGB());
            mc.fontRendererObj.drawString(s1, l1, k, 553648127);
            if (false) {
                mc.fontRendererObj.drawString(s2, l - mc.fontRendererObj.getStringWidth(s2), k, 553648127);
            }

            if (j == collection.size()) {
                String s3 = objective.getDisplayName();
                RenderUtils.drawRect(l1 - 2, k - 13, l, k - 1, new Color(64, 128, 255, 200).getRGB());
                scoreboardx1 = l;
                if (!first) {
                    scoreboardx = RenderUtils.width() - (l1 - 2);
                    scoreboardy = RenderUtils.height() - (k - 13 + j * mc.fontRendererObj.FONT_HEIGHT);
                    first = true;
                }
                RenderUtils.drawRect(l1 - 2, k - 1, l, k, 1342177280);
                mc.fontRendererObj.drawString(s3, (l1 - 2) + 5, k - mc.fontRendererObj.FONT_HEIGHT - 2, 553648127);
            }
        }
        thisj = jtemp;
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

            int r = rank;
            if (r < 95) {
                r += 100;
            }

            float mspeed = HUD.bps[rank];

            if (Client.instance.inDevelopment && false) {
                int dick = 0;
                float dick1 = fucktest[rank];
                float dick2 = mspeed;
                while (dick1 != 0) {
                    dick1 = RenderUtils.toanim1(dick1, dick2, 0, 2, 0.1f);
                    dick++;
                }
                dick = 95 - dick;

                if (i > dick) {
                    fucktest[rank] = RenderUtils.toanim1(fucktest[rank], dick2, 0, 2, 0.1f);
                } else {
                    fucktest[rank] = RenderUtils.toanim(fucktest[rank], HUD.bps[rank], 32, 0.1f);
                }

                float mspeed1 = fucktest[rank];
                RenderUtils.drawRect(StartXspeed + 95 - i, (int) (StartYspeed + (62 - (mspeed1 / xnum))), StartXspeed + 96 - i, StartYspeed + 62, new Color(255, 255, 255, 128).getRGB());

            } else {
                RenderUtils.drawRect(StartXspeed + 95 - i, (int) (StartYspeed + (62 - (mspeed / xnum))), StartXspeed + 96 - i, StartYspeed + 62, new Color(255, 255, 255, 128).getRGB());
            }
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

    @Override
    public void onGuiClosed() {
        open = false;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        open = true;
        float x, y;
        if (isHovered(keystrokesx, keystrokesy - 12, keystrokesx + 75, keystrokesy, mouseX, mouseY) && !keydown) {
            x = keystrokesx;
            y = keystrokesy;
            check = 4;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (isHovered(bpsavgstartx, bpsavgstarty, bpsavgstartx + 96, bpsavgstarty + 12, mouseX, mouseY) && !keydown) {
            x = bpsavgstartx;
            y = bpsavgstarty;
            check = 2;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (isHovered(dotstartx, dotstarty, dotstartx + 64, dotstarty + 12, mouseX, mouseY) && !keydown) {
            x = dotstartx;
            y = dotstarty;
            check = 1;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        //RenderUtils.drawRect(RenderUtils.width() - scoreboardx, RenderUtils.height() - scoreboardy - thisj * mc.fontRendererObj.FONT_HEIGHT, scoreboardx1, RenderUtils.height() - scoreboardy + 12 - thisj * mc.fontRendererObj.FONT_HEIGHT,-1);
        if (isHovered(RenderUtils.width() - scoreboardx, RenderUtils.height() - scoreboardy - thisj * mc.fontRendererObj.FONT_HEIGHT, scoreboardx1, RenderUtils.height() - scoreboardy + 12 - thisj * mc.fontRendererObj.FONT_HEIGHT, mouseX, mouseY) && !keydown) {
            x = RenderUtils.width() - scoreboardx;
            y = RenderUtils.height() - scoreboardy - thisj * mc.fontRendererObj.FONT_HEIGHT;
            check = 3;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (check == 1) {
            dotstartx = mouseX - keydownX;
            dotstarty = mouseY - keydownY;
        }

        if (check == 2) {
            bpsavgstartx = mouseX - keydownX;
            bpsavgstarty = mouseY - keydownY;
        }

        if (check == 3) {
            scoreboardx = RenderUtils.width() - (mouseX - keydownX);
            scoreboardy = RenderUtils.height() - (mouseY - keydownY + thisj * mc.fontRendererObj.FONT_HEIGHT);
        }

        if (check == 4) {
            keystrokesx = mouseX - keydownX;
            keystrokesy = mouseY - keydownY;
        }

        if (!Mouse.isButtonDown(0)) {
            check = 0;
            keydown = false;
            keydownX = mouseX;
            keydownY = mouseY;
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
