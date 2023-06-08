package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageLoader {
    private static final Logger logger = LogManager.getLogger();
    public static ResourceLocation circle;
    public static ResourceLocation circle_leftup;
    public static ResourceLocation circle_leftdown;
    public static ResourceLocation circle_rightup;
    public static ResourceLocation circle_rightdown;
    public static ResourceLocation tohru0;
    public static ResourceLocation tohru1;
    public static ResourceLocation tohru0round;
    public static ResourceLocation background;
    public static ResourceLocation language;

    public static long starttime;
    public static float time;

    public static void init() {
        starttime = System.nanoTime();
        circle = new ResourceLocation("dot/circle.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(circle);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/circle.png " + time + "ms");

        starttime = System.nanoTime();
        circle_leftup = new ResourceLocation("dot/circle_leftup.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(circle_leftup);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/circle_leftup.png " + time + "ms");

        starttime = System.nanoTime();
        circle_leftdown = new ResourceLocation("dot/circle_leftdown.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(circle_leftdown);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/circle_leftdown.png " + time + "ms");

        starttime = System.nanoTime();
        circle_rightup = new ResourceLocation("dot/circle_rightup.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(circle_rightup);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/circle_rightup.png " + time + "ms");

        starttime = System.nanoTime();
        circle_rightdown = new ResourceLocation("dot/circle_rightdown.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(circle_rightdown);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/circle_rightdown.png " + time + "ms");

        starttime = System.nanoTime();
        tohru0 = new ResourceLocation("dot/tohru0.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(tohru0);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/tohru0.png " + time + "ms");

        starttime = System.nanoTime();
        tohru1 = new ResourceLocation("dot/tohru1.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(tohru1);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/tohru1.png " + time + "ms");

        starttime = System.nanoTime();
        tohru0round = new ResourceLocation("dot/tohru0round.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(tohru0round);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/tohru0round.png " + time + "ms");

        starttime = System.nanoTime();
        background = new ResourceLocation("dot/background.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(background);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/background.png " + time + "ms");

        starttime = System.nanoTime();
        language = new ResourceLocation("dot/language.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(language);
        time = ((System.nanoTime() - starttime) / 1000000f);
        logger.info("[Dot] Load dot/language.png " + time + "ms");
    }
}
