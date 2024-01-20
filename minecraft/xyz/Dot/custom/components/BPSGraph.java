package xyz.Dot.custom.components;

import xyz.Dot.custom.base.Component;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.ui.Custom;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;

public class BPSGraph extends Component {
    public BPSGraph() {
        super(96, 12, "BPSGraph");
    }


    @Override
    public void drawHUD(float x, float y, float partialTicks) {

        int StartXspeed = (int) x;
        int StartYspeed = (int) y;
        ShaderManager.addBlurTask(() -> {
            RenderUtils.drawRect(StartXspeed, StartYspeed + 12, StartXspeed + 96, StartYspeed + 62, new Color(0, 0, 0, 64).getRGB());
            RenderUtils.drawHalfRoundRect(StartXspeed, StartYspeed, StartXspeed + 96, StartYspeed + 12, 4,  HUD.transparent.isToggle() ? new Color(0,0,0,100) : CustomColor.getColor());

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
        FontLoaders.normalfont10.drawString(maxstring, StartXspeed + 96 - FontLoaders.normalfont10.getStringWidth(maxstring) - 2, StartYspeed + 16, new Color(0, 0, 0, 128).getRGB());

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
                float dick1 = Custom.fucktest[rank];
                float dick2 = mspeed;
                while (dick1 != 0) {
                    dick1 = RenderUtils.toanim1(dick1, dick2, 0, 2, 0.1f);
                    dick++;
                }
                dick = 95 - dick;
                if (i > dick) {
                    //fucktest[rank] = RenderUtils.toanim1(fucktest[rank], dick2, 0, 32, 0.1f);
                } else {
                    Custom.fucktest[rank] = RenderUtils.toanim2(Custom.fucktest[rank], 200, HUD.bps[rank], 8, 0.01f, 0.01f);
                }

                float mspeed1 = Custom.fucktest[rank];
                RenderUtils.drawRect(StartXspeed + 95 - i, (int) (StartYspeed + (62 - (mspeed1 / xnum))), StartXspeed + 96 - i, StartYspeed + 62, new Color(255, 255, 255, 128).getRGB());

            } else {
                RenderUtils.drawRect(StartXspeed + 95 - i, (int) (StartYspeed + (62 - (mspeed / xnum))), StartXspeed + 96 - i, StartYspeed + 62, new Color(255, 255, 255, 128).getRGB());
            }
        }

        float avg = 0;
        for (float ax : avglist) {
            avg += ax;
        }
        avg = Math.round((avg / 96) * 100) / 100.0f;
        HUD.avgspeed = avg;

        String mtext = Translator.getInstance().m("BPS.AVG") + ": " + avg;
        FontLoaders.normalfont16.drawString(mtext, StartXspeed + 5, StartYspeed + 4, new Color(255, 255, 255).getRGB());
        int num = 0;

    }
}
