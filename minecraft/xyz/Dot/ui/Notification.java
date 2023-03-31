package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.TimerUtil;

import java.awt.*;
import java.util.ArrayList;

public class Notification {

    public static ArrayList<Notification> notifications = new ArrayList<>();
    Type type;
    private String message;
    private TimerUtil timer;
    private double posY;
    private double width;
    private double height;
    private double animationX;

    public Notification(final String message, final Type type) {
        this.message = message;
        (this.timer = new TimerUtil()).reset();
        final CFontRenderer font = FontLoaders.normalfont16;
        this.width = font.getStringWidth(message) + 20;
        this.height = 20.0;
        this.animationX = this.width;
        this.posY = -1.0;
        this.type = type;
    }

    public static void sendClientMessage(String message, Notification.Type type) {
        notifications.add(new Notification(message, type));
    }

    public static void drawNotifications() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        double startY = res.getScaledHeight() - 50;
        final double lastY = startY;
        for (int i = 0; i < notifications.size(); i++) {
            Notification not = notifications.get(i);
            if (not.shouldDelete()) {
                notifications.remove(i);
            }
            not.draw(startY, lastY);
            startY -= not.getHeight() + 5;
        }
    }

    public void draw(double getY, final double lastY) {
        if (this.isFinished()) {
            this.animationX = RenderUtils.toanim1((float) this.animationX, 0f, (float) this.width, 20, 1f);
        } else {
            this.animationX = RenderUtils.toanim((float) this.animationX, 0.0f, 20, 0.1f);
        }

        float p = (float) (1 - this.animationX / this.width);

        if (this.posY == -1.0) {
            this.posY = 0;
        } else {
            this.posY = RenderUtils.toanim((float) this.posY, (float) getY, 10, 0.1f);
        }
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        final int x1 = (int) (res.getScaledWidth() - this.width + this.animationX) - 5;
        final int x2 = (int) (x1 + this.width);
        final int y1 = (int) this.posY;
        final int y2 = (int) (y1 + this.height);
        RenderUtils.drawRect(x1, y1, x2, y2, new Color(255, 255, 255, (int) (1 * 235)).getRGB());
        int test = (int) ((1 - (timer.gettime() / Math.max(FontLoaders.normalfont16.getStringWidth(message) * 20f, 2000f))) * (x2 - x1) + x1);
        RenderUtils.drawRect(x1, y2 - 2, test > x1 ? test : x1, y2, new Color(0, 0, 0, (int) (p * 255)).getRGB());
        CFontRenderer font = FontLoaders.normalfont16;
        Color c;
        int alpha = (int) (p * 255);
        if (this.type == Type.SUCCESS) {
            c = new Color(64, 128, 255, alpha);
        } else if (this.type == Type.ERROR) {
            c = new Color(255, 64, 64, alpha);
        } else if (this.type == Type.WARNING) {
            c = new Color(255, 128, 64, alpha);
        } else {
            c = new Color(50, 50, 50, alpha);
        }
        RenderUtils.drawFilledCircle(x1 + 8, y1 + 10, 3, c);
        font.drawCenteredString(this.message, x1 + font.getStringWidth(message) / 2 + 15, (float) (y1 + this.height * (1 - 0.618)), new Color(0, 0, 0, (int) (p * 255)).getRGB());
    }

    public boolean shouldDelete() {
        return this.isFinished() && this.animationX == this.width;
    }

    private boolean isFinished() {
        return this.timer.hasReached(Math.max(FontLoaders.normalfont16.getStringWidth(message) * 20, 2000));
    }

    public double getHeight() {
        return this.height;
    }

    public enum Type {
        SUCCESS("SUCCESS", 0),
        INFO("INFO", 1),
        WARNING("WARNING", 2),
        ERROR("ERROR", 3);

        Type(final String s, final int n) {
        }
    }
}
