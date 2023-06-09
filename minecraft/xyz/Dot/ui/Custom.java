package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import org.lwjgl.input.Mouse;
import xyz.Dot.Client;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.module.Misc.KeyStrokes;
import xyz.Dot.module.Render.BetterScoreboard;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.shader.BloomUtil;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;

public class Custom extends GuiScreen {

    static CFontRenderer font = FontLoaders.normalfont16;
    static CFontRenderer font1 = FontLoaders.normalfont10;
    static Minecraft mc = Minecraft.getMinecraft();
    // static int dotstartx = 20;
    // static int dotstarty = 25;
    // static int bpsavgstartx = 20;
    // static int bpsavgstarty = 96;
    // static int scoreboardx;
    static int scoreboardx1;
    // static int scoreboardy;
    static boolean first = false;
    boolean keydown = false;
    int check = 0;
    static int thisj = 0;
    int keydownX, keydownY;
    // static int keystrokesx = 20, keystrokesy = 200;
    public static ArrayList<Long> cpsl = new ArrayList<>();
    public static ArrayList<Long> cpsr = new ArrayList<>();
    public static boolean cpsldown;
    public static boolean cpsrdown;
    public static boolean open = false;

    public static float[] fucktest = new float[12800];
    static float wc = 0f;
    static float sc = 0f;
    static float ac = 0f;
    static float dc = 0f;
    static float lc = 0f;
    static float rc = 0f;
    static float spacec = 0f;
    static float shiftc = 0f;

    public static void drawKeyStrokes(boolean blur) {
        int x = (int) KeyStrokes.x.getCurrentValue();
        int y = (int) KeyStrokes.y.getCurrentValue();
        int background = 0;
        int press = 255;

        Minecraft minecraft = Minecraft.getMinecraft();
        GameSettings gameSettings = minecraft.gameSettings;

        int multiplier = 6;

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
        int finalY = y;
        int finalY1 = y;
        int round = 2;
        if(blur){
            ShaderManager.addBlurTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x + 28, finalY, x + 53, finalY1 + 25, round, new Color((int) wc, (int) wc, (int) wc, 128));
                }
            });
        }
        int finalY2 = y;
        int finalY5 = y;
        if(blur) {
            ShaderManager.addBloomTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x + 28, finalY2, x + 53, finalY5 + 25, round, new Color((int) wc, (int) wc, (int) wc, 128));
                }
            });
        }
        RenderUtils.drawRoundRect(x + 28, y, x + 53, y + 25, round, new Color((int) wc, (int) wc, (int) wc, 128));
        font.drawCenteredString(s, x + 28 + 25 / 2f, y + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "S";
        int finalY3 = y;
        if(blur) {
            ShaderManager.addBlurTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x + 28, finalY3 + 28, x + 53, finalY3 + 53, round, new Color((int) sc, (int) sc, (int) sc, 128));
                }
            });

            int finalY4 = y;
            ShaderManager.addBloomTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x + 28, finalY4 + 28, x + 53, finalY4 + 53, round, new Color((int) sc, (int) sc, (int) sc, 128));
                }
            });
        }
        RenderUtils.drawRoundRect(x + 28, y + 28, x + 53, y + 53, round, new Color((int) sc, (int) sc, (int) sc, 128));
        font.drawString(s, x + 28 + (25 - font.getStringWidth(s)) / 2, y + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "A";
        int finalY6 = y;
        if(blur) {
            ShaderManager.addBlurTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x, finalY6 + 28, x + 25, finalY6 + 53, round, new Color((int) ac, (int) ac, (int) ac, 128));
                }
            });
            int finalY7 = y;
            ShaderManager.addBloomTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x, finalY7 + 28, x + 25, finalY7 + 53, round, new Color((int) ac, (int) ac, (int) ac, 128));
                }
            });
        }
        RenderUtils.drawRoundRect(x, y + 28, x + 25, y + 53, round, new Color((int) ac, (int) ac, (int) ac, 128));
        font.drawCenteredString(s, x + 25 / 2f, y + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "D";
        int finalY8 = y;
        if(blur) {
            ShaderManager.addBlurTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x + 56, finalY8 + 28, x + 81, finalY8 + 53, round, new Color((int) dc, (int) dc, (int) dc, 128));
                }
            });
            int finalY9 = y;
            ShaderManager.addBloomTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x + 56, finalY9 + 28, x + 81, finalY9 + 53, round, new Color((int) dc, (int) dc, (int) dc, 128));
                }
            });
        }
        RenderUtils.drawRoundRect(x + 56, y + 28, x + 81, y + 53, round, new Color((int) dc, (int) dc, (int) dc, 128));
        font.drawCenteredString(s, x + 56 + 25 / 2f, y + 28 + (25 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        y += 56;
        s = "LMB";
        int finalY10 = y;
        int finalY11 = y;
        if(blur) {
            ShaderManager.addBlurTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x, finalY10, x + (81 - 3) / 2, finalY11 + 25, round, new Color((int) lc, (int) lc, (int) lc, 128));
                }
            });
            int finalY12 = y;
            int finalY13 = y;
            ShaderManager.addBloomTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x, finalY12, x + (81 - 3) / 2, finalY13 + 25, round, new Color((int) lc, (int) lc, (int) lc, 128));
                }
            });
        }
        RenderUtils.drawRoundRect(x, y, x + (81 - 3) / 2, y + 25, round, new Color((int) lc, (int) lc, (int) lc, 128));
        font.drawCenteredString(s, x + (81 - 3) / 2 / 2f, y + (20 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());
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
        s = String.valueOf(count);
        font1.drawCenteredString(s, x + (81 - 3) / 2 / 2f, y + 16 + (5 - font1.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        s = "RMB";
        int finalY14 = y;
        if(blur) {
            int finalY15 = y;
            ShaderManager.addBlurTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x + (81 + 3) / 2, finalY14, x + 81, finalY15 + 25, round, new Color((int) rc, (int) rc, (int) rc, 128));
                }
            });
            int finalY16 = y;
            int finalY17 = y;
            ShaderManager.addBloomTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x + (81 + 3) / 2, finalY16, x + 81, finalY17 + 25, round, new Color((int) rc, (int) rc, (int) rc, 128));
                }
            });
        }
        RenderUtils.drawRoundRect(x + (81 + 3) / 2, y, x + 81, y + 25, round, new Color((int) rc, (int) rc, (int) rc, 128));
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
        int finalY18 = y;
        int finalY19 = y;
        if(blur) {
            ShaderManager.addBlurTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x, finalY18, x + 81, finalY19 + 15, round, new Color((int) spacec, (int) spacec, (int) spacec, 128));

                }
            });
            int finalY20 = y;
            int finalY21 = y;
            ShaderManager.addBloomTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x, finalY20, x + 81, finalY21 + 15, round, new Color((int) spacec, (int) spacec, (int) spacec, 128));

                }
            });
        }
        RenderUtils.drawRoundRect(x, y, x + 81, y + 15, round, new Color((int) spacec, (int) spacec, (int) spacec, 128));
        font.drawCenteredString(s, x + 81 / 2f, y + (15 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

        y += 18;
        s = "Sneak";
        int finalY22 = y;
        int finalY23 = y;
        if(blur) {
            ShaderManager.addBlurTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x, finalY22, x + 81, finalY23 + 15, round, new Color((int) shiftc, (int) shiftc, (int) shiftc, 128));

                }
            });
            int finalY24 = y;
            int finalY25 = y;
            ShaderManager.addBloomTask(new Runnable() {
                @Override
                public void run() {
                    RenderUtils.drawRoundRect(x, finalY24, x + 81, finalY25 + 15, round, new Color((int) shiftc, (int) shiftc, (int) shiftc, 128));

                }
            });
        }
        RenderUtils.drawRoundRect(x, y, x + 81, y + 15, round, new Color((int) shiftc, (int) shiftc, (int) shiftc, 128));
        font.drawString(s, x + (81 - font.getStringWidth(s)) / 2, y + (15 - font.getStringHeight(s)) / 2 + 1, new Color(255, 255, 255).getRGB());

    }

    public static void ks() {
        RenderUtils.drawHalfRoundRect((int) KeyStrokes.x.getCurrentValue(), (int) KeyStrokes.y.getCurrentValue() - 12, (int) KeyStrokes.x.getCurrentValue() + 81, (int) KeyStrokes.y.getCurrentValue(), 4, HUD.transparent.isToggle() ? new Color(0,0,0,100) : CustomColor.getColor());
        RenderUtils.drawRect((int) KeyStrokes.x.getCurrentValue(), (int) KeyStrokes.y.getCurrentValue(), (int) KeyStrokes.x.getCurrentValue() + 81, (int) KeyStrokes.y.getCurrentValue() + 117, new Color(0, 0, 0, 64).getRGB());
        font.drawString("KeyStrokes", (int) KeyStrokes.x.getCurrentValue() + 5, (int) KeyStrokes.y.getCurrentValue() - 12 + 4, new Color(255, 255, 255).getRGB());

    }
    private static Framebuffer bloomFramebuffer = new Framebuffer(1, 1, false);

    public static void drawScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
        int x = (int) BetterScoreboard.x.getCurrentValue();
        int y = (int) BetterScoreboard.y.getCurrentValue();
        final Scoreboard scoreboard = objective.getScoreboard();
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

            width = Math.max(width, fontRenderer.getStringWidth(playerName) + 4);
        }

        final int height = 11 + list.size() * Minecraft.fontRendererObj.FONT_HEIGHT;
        double finalWidth = width;
        if(HUD.blur.isToggle()){
            mc.getFramebuffer().bindFramebuffer(false);
            GuiIngame.checkSetupFBO(mc.getFramebuffer());
            glClear(GL_STENCIL_BUFFER_BIT);
            glEnable(GL_STENCIL_TEST);

            glStencilFunc(GL_ALWAYS, 1, 1);
            glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
            glColorMask(false, false, false, false);
            RenderUtils.drawRoundRect(x, y, (int) (x + width), y + height + 16,4, new Color(0, 0, 0, 64));

            glColorMask(true, true, true, true);
            glStencilFunc(GL_EQUAL, 1, 1);
            glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
            ShaderManager.renderBlur(8);
            glDisable(GL_STENCIL_TEST);
        }
        if(HUD.shadow.isToggle()){
            bloomFramebuffer = ShaderManager.createFrameBuffer(bloomFramebuffer);
            bloomFramebuffer.framebufferClear();
            bloomFramebuffer.bindFramebuffer(true);
            RenderUtils.drawRoundRect(x, y, (int) (x + width), y + height + 16,4, new Color(0, 0, 0, 64));

            bloomFramebuffer.unbindFramebuffer();
            BloomUtil.renderBlur(bloomFramebuffer.framebufferTexture, 10, 2);
        }
        RenderUtils.drawRoundRect(x, y, (int) (x + width), y + height + 16,4, new Color(0, 0, 0, 64));
        RenderUtils.drawHalfRoundRect(x, y, (int) (x + width), y + 12, 4, HUD.transparent.isToggle() ? new Color(0,0,0,100) : CustomColor.getColor());
        font.drawString("Scoreboard", x + 5, y + 4, new Color(255, 255, 255).getRGB());
        scoreboardx1 = (int) (x + width);
        final int sb =y + 14;

        for (int i = 0; i < length; i++) {
           fontRenderer.drawStringWithShadow(formattedPlayerNames[i], (float) ((double) x + 2), sb + height - (i + 1) * 9, 0xFFFFFFFF);
        }

        fontRenderer.drawStringWithShadow(objective.getDisplayName(),
                (float) ((double) x + width / 2.0F - fontRenderer.getStringWidth(objective.getDisplayName()) / 2.0F),
                sb + 2, 0xFFFFFFFF);
    }

    public static void drawDot() {
        Minecraft mc = Minecraft.getMinecraft();
        String CName = Client.instance.client_name;

        int StartX = (int) HUD.dotx.getCurrentValue();
        int StartY = (int) HUD.doty.getCurrentValue();
        int finalStartY = StartY;
        int finalStartY1 = StartY;
        ShaderManager.addBlurTask(new Runnable() {
            @Override
            public void run() {
                RenderUtils.drawRoundRect(StartX, finalStartY, StartX + 64, finalStartY1 + 56,4, new Color(0, 0, 0, 64));

            }
        });
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
            font.drawString(CName, StartX + 14, StartY + 4, new Color(255, 255, 255).getRGB());
            StartY += 20;
            font.drawString("FPS: " + mc.getDebugFPS(), StartX + 5, StartY, new Color(255, 255, 255).getRGB());
            StartY += 12;
            String ping = String.valueOf(mc.getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime());
            if (ping.equals("0")) {
                ping = "Failed";
            }
            font.drawString("PING: " + ping, StartX + 5, StartY, new Color(255, 255, 255).getRGB());
            StartY += 12;
            font.drawString("BPS: " + HUD.movespeed, StartX + 5, StartY, new Color(255, 255, 255).getRGB());
        }catch (Exception e){

        }

    }

    public static void drawBPSAVG() {
        int StartXspeed = (int) HUD.bpsx.getCurrentValue();
        int StartYspeed = (int) HUD.bpsy.getCurrentValue();
        ShaderManager.addBlurTask(new Runnable() {
            @Override
            public void run() {
                RenderUtils.drawRect(StartXspeed, StartYspeed + 12, StartXspeed + 96, StartYspeed + 62, new Color(0, 0, 0, 64).getRGB());
                RenderUtils.drawHalfRoundRect(StartXspeed, StartYspeed, StartXspeed + 96, StartYspeed + 12, 4,  HUD.transparent.isToggle() ? new Color(0,0,0,100) : CustomColor.getColor());

            }
        });
        RenderUtils.drawRect(StartXspeed, StartYspeed + 12, StartXspeed + 96, StartYspeed + 62, new Color(0, 0, 0, 64).getRGB());

        RenderUtils.drawHalfRoundRect(StartXspeed, StartYspeed, StartXspeed + 96, StartYspeed + 12, 4,  HUD.transparent.isToggle() ? new Color(0,0,0,164) : CustomColor.getColor());
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

            if (false) {
                int dick = 0;
                float dick1 = fucktest[rank];
                float dick2 = mspeed;
                while (dick1 != 0) {
                    dick1 = RenderUtils.toanim1(dick1, dick2, 0, 2, 0.1f);
                    dick++;
                }
                dick = 95 - dick;
                if (i > dick) {
                    //fucktest[rank] = RenderUtils.toanim1(fucktest[rank], dick2, 0, 32, 0.1f);
                } else {
                    fucktest[rank] = RenderUtils.toanim2(fucktest[rank], 200, HUD.bps[rank], 8, 0.01f, 0.01f);
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
        if (isHovered((int) KeyStrokes.x.getCurrentValue(), (int) KeyStrokes.y.getCurrentValue() - 12, (int) KeyStrokes.x.getCurrentValue() + 75, (int) KeyStrokes.y.getCurrentValue(), mouseX, mouseY) && !keydown) {
            x = (int) KeyStrokes.x.getCurrentValue();
            y = (int) KeyStrokes.y.getCurrentValue();
            check = 4;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (isHovered((int) HUD.bpsx.getCurrentValue(), (int) HUD.bpsy.getCurrentValue(), (int) HUD.bpsx.getCurrentValue() + 96, (int) HUD.bpsy.getCurrentValue() + 12, mouseX, mouseY) && !keydown) {
            x = (int) HUD.bpsx.getCurrentValue();
            y = (int) HUD.bpsy.getCurrentValue();
            check = 2;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (isHovered((int) HUD.dotx.getCurrentValue(), (int) HUD.doty.getCurrentValue(), (int) HUD.dotx.getCurrentValue() + 64, (int) HUD.doty.getCurrentValue() + 12, mouseX, mouseY) && !keydown) {
            x = (int) HUD.dotx.getCurrentValue();
            y = (int) HUD.doty.getCurrentValue();
            check = 1;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (isHovered((float) BetterScoreboard.x.getCurrentValue(), (float) BetterScoreboard.y.getCurrentValue(), scoreboardx1, (float) BetterScoreboard.y.getCurrentValue() + 12, mouseX, mouseY) && !keydown) {
            x = (float) BetterScoreboard.x.getCurrentValue();
            y = (float) BetterScoreboard.y.getCurrentValue();
            check = 3;
            keydown = true;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        if (check == 1) {
            HUD.dotx.setCurrentValue(mouseX - keydownX);
            HUD.doty.setCurrentValue(mouseY - keydownY);
        }

        if (check == 2) {
            HUD.bpsx.setCurrentValue(mouseX - keydownX);
            HUD.bpsy.setCurrentValue(mouseY - keydownY);
        }

        if (check == 3) {
            BetterScoreboard.x.setCurrentValue(mouseX - keydownX);
            BetterScoreboard.y.setCurrentValue(mouseY - keydownY);
        }

        if (check == 4) {
            KeyStrokes.x.setCurrentValue(mouseX - keydownX);
            KeyStrokes.y.setCurrentValue(mouseY - keydownY);
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
