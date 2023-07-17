package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.Dot.utils.UserUtils;

import java.util.ArrayList;

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
    public static ResourceLocation drag;
    public static ArrayList<ImageNeedLoad> needLoads = new ArrayList<>();

    public static void init() {
        circle = new ImageNeedLoad("dot/circle.png").rl;
        circle_leftup = new ImageNeedLoad("dot/circle_leftup.png").rl;
        circle_leftdown = new ImageNeedLoad("dot/circle_leftdown.png").rl;
        circle_rightup = new ImageNeedLoad("dot/circle_rightup.png").rl;
        circle_rightdown = new ImageNeedLoad("dot/circle_rightdown.png").rl;

        if (false) {  //暂时没用
            tohru0 = new ImageNeedLoad("dot/tohru0.png").rl;
        }

        if (UserUtils.name == null || !UserUtils.isLoadOK()) {
            tohru1 = new ImageNeedLoad("dot/tohru1.png").rl;
        }

        tohru0round = new ImageNeedLoad("dot/tohru0round.png").rl;
        background = new ImageNeedLoad("dot/background.png").rl;
        language = new ImageNeedLoad("dot/language.png").rl;
        drag = new ImageNeedLoad("dot/drag.png").rl;
    }
}
