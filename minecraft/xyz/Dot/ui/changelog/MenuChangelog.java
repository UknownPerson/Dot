package xyz.Dot.ui.changelog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuChangelog {

    public static final MenuChangelog instance = new MenuChangelog(2,2);

    private final List<Changelog> changelogs = new ArrayList<>();
    private final int y;
    private final int x;
    private final FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

    public MenuChangelog(final int x, final int y) {
        this.x = x;
        this.y = y;
        Changelog announcement = new Changelog("公告");
        announcement.addUpdate(Changelog.Type.Update,"如果你想要随时接收更新，请加入我们的QQ群632512814！");
        Changelog v010423 = new Changelog("更新日志 0.4 - 2023.6.13");
        v010423.addUpdate(Changelog.Type.Add,"添加了自动更新，现在无需重新下载客户端");


        changelogs.add(v010423);


        changelogs.add(announcement);
        Collections.reverse(changelogs);
    }

    public void draw() {
        int changeY = 0;
        for (Changelog c : changelogs) {
            font.drawString(c.getTitle(), x, y + changeY, -1);
            int set = 2;
            for (String s : c.getUpdates()) {
                font.drawString(s, x, changeY + set + font.FONT_HEIGHT + 2, Color.WHITE.getRGB());
                set += font.FONT_HEIGHT ;
            }
            changeY +=( c.getUpdates().size() * font.FONT_HEIGHT) + font.FONT_HEIGHT + 4;
        }
    }
}
