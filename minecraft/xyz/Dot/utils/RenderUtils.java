package xyz.Dot.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.Dot.ui.ImageLoader;


import java.awt.*;

public class RenderUtils {
    public static float fps;
    public static float ms;
    public static void drawCube(final AxisAlignedBB axisAlignedBB) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(7, DefaultVertexFormats.POSITION);

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();

        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawRect(int x, int y, int x1, int y1, int color) {
        Gui.drawRect(x, y, x1, y1, color);
    }

    public static void drawRect(float x, float y, float x1, float y1, int color) {
        Gui.drawRect((int) x, (int) y, (int) x1, (int) y1, color);
    }

    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawRoundRect1(int x1, int y1, int x2, int y2, int roundsize, Color color) {
        int x1t = x1 + roundsize;
        int y1t = y1 + roundsize;
        int x2t = x2 - roundsize;
        int y2t = y2 - roundsize;
        if (x1 != x2 && y1 != y2) {
            Gui.drawRect(x1t, y1t, x2t, y2t, color.getRGB());
            Gui.drawRect(x1t, y1, x2t, y1t, color.getRGB());
            Gui.drawRect(x2t, y1t, x2, y2t, color.getRGB());
            Gui.drawRect(x1t, y2t, x2t, y2, color.getRGB());
            Gui.drawRect(x1, y1t, x1t, y2t, color.getRGB());
            drawFilledCircle1(x1t, y1t, roundsize, color);
            drawFilledCircle1(x2t, y1t, roundsize, color);
            drawFilledCircle1(x1t, y2t, roundsize, color);
            drawFilledCircle1(x2t, y2t, roundsize, color);
        }
    }

    public static void drawRoundRect(int x1, int y1, int x2, int y2, int roundsize, Color color) {


        int x1t = x1 + roundsize;
        int y1t = y1 + roundsize;
        int x2t = x2 - roundsize;
        int y2t = y2 - roundsize;
        if (x1 != x2 && y1 != y2) {
            Gui.drawRect(x1t, y1t, x2t, y2t, color.getRGB());
            Gui.drawRect(x1t, y1, x2t, y1t, color.getRGB());
            Gui.drawRect(x2t, y1t, x2, y2t, color.getRGB());
            Gui.drawRect(x1t, y2t, x2t, y2, color.getRGB());
            Gui.drawRect(x1, y1t, x1t, y2t, color.getRGB());
            drawImage(ImageLoader.circle_leftup, x1, y1, roundsize, roundsize, color);
            drawImage(ImageLoader.circle_leftdown, x1, y2-roundsize, roundsize, roundsize, color);
            drawImage(ImageLoader.circle_rightup, x2-roundsize, y1, roundsize, roundsize, color);
            drawImage(ImageLoader.circle_rightdown, x2-roundsize, y2-roundsize, roundsize, roundsize, color);

        }
    }

    public static void drawHalfRoundRect(int x1, int y1, int x2, int y2, int roundsize, Color color) {
        int x1t = x1 + roundsize;
        int y1t = y1 + roundsize;
        int x2t = x2 - roundsize;
        int y2t = y2 - roundsize;
        if (x1 != x2 && y1 != y2) {
            Gui.drawRect(x1t, y1, x2t, y1t, color.getRGB());
            Gui.drawRect(x1, y1t, x2, y2, color.getRGB());
            drawImage(ImageLoader.circle_leftup, x1, y1, roundsize, roundsize, color);
            drawImage(ImageLoader.circle_rightup, x2-roundsize, y1, roundsize, roundsize, color);
        }
    }

    public static void drawFilledCircle1(final int n, final int n2, final int n3, final Color color) {
        final int n4 = n3 * 10;
        final double n5 = 2 * Math.PI / n4;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < n4; ++i) {
            final float n6 = (float) (n3 * Math.sin(i * n5));
            final float n7 = (float) (n3 * Math.cos(i * n5));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
                    color.getAlpha() / 255.0f);
            GL11.glVertex2f(n + n6, n2 + n7);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle(int x, int y, int round, Color color) {
        drawImage(ImageLoader.circle, x - round, y - round, round * 2, round * 2, color);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable((int) 2929);
        GL11.glEnable((int) 3042);
        GL11.glDepthMask((boolean) false);
        OpenGlHelper.glBlendFunc((int) 770, (int) 771, (int) 1, (int) 0);
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, (float) 0.0f, (float) 0.0f, (int) width, (int) height, (float) width, (float) height);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 2929);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height, Color color) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable((int) 2929);
        GL11.glEnable((int) 3042);
        GL11.glDepthMask((boolean) false);
        OpenGlHelper.glBlendFunc((int) 770, (int) 771, (int) 1, (int) 0);
        GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getGreen() / 255.0f), (float) ((float) color.getBlue() / 255.0f), ((float) color.getAlpha() / 255.0f));
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, (float) 0.0f, (float) 0.0f, (int) width, (int) height, (float) width, (float) height);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 2929);
    }

    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }

    public static int height() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
    }

    public static float toanim(float now, float end, float multiplier, float min) {
        float beterspeedinfps = 120.0f / fps;
        float speed = Math.max((Math.abs(now - end) / multiplier), min) * beterspeedinfps;
        if (now < end) {
            if (now + speed > end) {
                now = end;
            } else {
                now += speed;
            }
        } else if (now > end) {
            if (now - speed < end) {
                now = end;
            } else {
                now -= speed;
            }
        }
        return now;
    }

    public static float toanim1(float now, float start, float end, float multiplier, float min) {
        float beterspeedinfps = 120.0f / fps;
        if ((now < start && now < end) || (now > start && now > end)) {
            now = start;
        }
        float speed = Math.max((Math.abs(start - now) / multiplier), min) * beterspeedinfps;
        if (now < end) {
            if (now + speed > end) {
                now = end;
            } else {
                now += speed;
            }
        } else if (now > end) {
            if (now - speed < end) {
                now = end;
            } else {
                now -= speed;
            }
        }
        return now;
    }

    public static float toanim2(float now, float start, float end, float multiplier, float min) {
        if (now > start) {
            if (now < (start + end) / 2) {
                now = toanim1(now, start, end, multiplier, min);
            } else {
                now = toanim(now, end, multiplier, min);
            }
        } else {
            if (now > (start + end) / 2) {
                now = toanim1(now, start, end, multiplier, min);
            } else {
                now = toanim(now, end, multiplier, min);
            }
        }
        return now;
    }

    public static float toanim2(float now, float start, float end, float multiplier, float min1, float min2) {
        if (now > start) {
            if (now < (start + end) / 2) {
                now = toanim1(now, start, end, multiplier, min2);
            } else {
                now = toanim(now, end, multiplier, min1);
            }
        } else {
            if (now > (start + end) / 2) {
                now = toanim1(now, start, end, multiplier, min2);
            } else {
                now = toanim(now, end, multiplier, min1);
            }
        }
        return now;
    }

    public static float toanimNoFps(float now, float end, float multiplier, float min) {
        float speed = Math.max((Math.abs(now - end) / multiplier), min);
        if (now < end) {
            if (now + speed > end) {
                now = end;
            } else {
                now += speed;
            }
        } else if (now > end) {
            if (now - speed < end) {
                now = end;
            } else {
                now -= speed;
            }
        }
        return now;
    }

    public static void doGlScissor(float x, float y, float x1, float y1) {
        float width = x1 - x;
        float height = y1 - y;
        Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;
        int k = mc.gameSettings.particleSetting;
        //k = mc.displayWidth / width();

        if (k == 0) {
            k = 1000;
        }

        while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320
                && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        GL11.glScissor((int) (x * scaleFactor), (int) (mc.displayHeight - (y + height) * scaleFactor),
                (int) (width * scaleFactor), (int) (height * scaleFactor));
    }
}
