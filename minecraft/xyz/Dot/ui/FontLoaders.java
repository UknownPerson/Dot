/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FontLoaders {
    private static HashMap fonts = new HashMap();
    public static CFontRenderer normalfont16 = new CFontRenderer(FontLoaders.getNormalFont(16), true, true);
    public static CFontRenderer normalfont20 = new CFontRenderer(FontLoaders.getNormalFont(20), true, true);
    public static CFontRenderer normalfont36 = new CFontRenderer(FontLoaders.getNormalFont(36), true, true);

    public static CFontRenderer comfortaafont16 = new CFontRenderer(FontLoaders.getComfortaaFont(16), true, true);


    private static Font getDefault(int size) {
        return new Font("default", 0, size);
    }

    public static UnicodeFontRenderer getUniFont(String s, float size, boolean b2) {
        UnicodeFontRenderer UnicodeFontRenderer = null;

        try {
            if (fonts.containsKey(s) && ((HashMap) fonts.get(s)).containsKey(size)) {
                return (UnicodeFontRenderer) ((HashMap) fonts.get(s)).get(size);
            }
            UnicodeFontRenderer = new UnicodeFontRenderer(Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("fonts/msyh.ttf")).getInputStream()).deriveFont(size), size, -1, -1, false);
            UnicodeFontRenderer.setUnicodeFlag(true);
            UnicodeFontRenderer.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
            HashMap hashMap = new HashMap();
            if (fonts.containsKey(s)) {
                hashMap.putAll((Map) fonts.get(s));
            }

            hashMap.put(size, UnicodeFontRenderer);
            fonts.put(s, hashMap);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return UnicodeFontRenderer;
    }

    private static Font getNormalFont(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("dot/font.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getComfortaaFont(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("dot/Comfortaa.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

}

