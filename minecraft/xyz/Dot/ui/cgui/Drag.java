package xyz.Dot.ui.cgui;

import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.ui.ImageLoader;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;


public class Drag {

    static float dragred = 0, draggreen = 0, dragblue = 0;

    public static void draw(float x, float y, boolean hovered) {

        int dragredto;
        int draggreento;
        int dragblueto;

        if (hovered) {
            dragredto = CustomColor.getColor().getRed();
            draggreento = CustomColor.getColor().getGreen();
            dragblueto = CustomColor.getColor().getBlue();
        } else {
            dragredto = 0;
            draggreento = 0;
            dragblueto = 0;
        }

        dragred = RenderUtils.toanim(dragred, dragredto, 8, 0.1f);
        draggreen = RenderUtils.toanim(draggreen, draggreento, 8, 0.1f);
        dragblue = RenderUtils.toanim(dragblue, dragblueto, 8, 0.1f);
        RenderUtils.drawImage(ImageLoader.drag, x, y, 10, 10, new Color((int)dragred, (int)draggreen, (int)dragblue, 64));
    }
}
