package xyz.Dot.ui.cgui;

import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.UserUtils;

import java.awt.*;

public class User {

    public static void draw(float x, float y, float width) {
        CFontRenderer font = FontLoaders.normalfont12;
        RenderUtils.drawImage(UserUtils.getUserProfilePhoto(), (int) x, (int) y, 16, 16);
        String pn = UserUtils.getALLPrefix() + UserUtils.getName();
        if((font.getStringWidth(pn) + 18) > width){
            while ((font.getStringWidth(pn + "...") + 18) > width){
                pn = pn.substring(0,pn.length() - 1);
            }
            pn = pn + "...";
        }
        font.drawString(pn,x + 18,y + 7,new Color(0,0,0).getRGB());
    }

}
