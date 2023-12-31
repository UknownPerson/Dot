package xyz.Dot.ui.obj.objs;

import xyz.Dot.module.Client.ClickGui;
import xyz.Dot.ui.CFont;
import xyz.Dot.ui.CFontRenderer;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.ui.obj.Obj;
import xyz.Dot.utils.RenderUtils;

import java.awt.*;

public class test extends Obj {

    public static double xf = 0;
    public static double yf = 0;
    public test() {
        super(0, 0, 100, 100, 0, 0.05);
    }

    @Override
    public void draw() {
        super.draw();
        double dx = this.getDrawx();
        double dy = this.getDrawy();
        RenderUtils.drawRect(dx, dy, dx + 50, dy + 50, -1);
        CFontRenderer font = FontLoaders.normalfont16;

        font.drawString("speed" + this.getSpeed(),dx,dy,new Color(0,0,0).getRGB());
        font.drawString("goal(" + this.getGoalx() + "," + this.getGoaly() + ")",dx,dy + 5,new Color(0,0,0).getRGB());
        font.drawString("now(" + this.getX() + "," + this.getY() + ")",dx,dy + 10,new Color(0,0,0).getRGB());

        //this.setPos(0,0);
        this.setGoalPos(300,200);
        //this.setGoalPos(xf,yf);
    }
}
