package xyz.Dot.custom.base;

import net.minecraft.client.Minecraft;
import xyz.Dot.custom.Component;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;
import java.util.function.Supplier;

public class TextComponent extends Component {
    private final Supplier<String> method;

    private final Setting background = new Setting(this.module,"Background",true);

    private static final int OFFSET = 4;

    public TextComponent(String name,Supplier<String> method) {
        super(0, 0, name);
        this.method = method;
        this.addValues(background);
    }

    @Override
    public void drawHUD(float x, float y, float partialTicks) {
        if(method.get().equals(""))
            return;
        setWidth(width());
        setHeight(height());
        if(HUD.blur.isToggle()){
            ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(x,y,x + width(),y + height(),4,new Color(0,0,0,100)));
        }
        if(HUD.shadow.isToggle()){
            ShaderManager.addBloomTask(() -> RenderUtils.drawRoundRect(x,y,x + width(),y + height(),4,new Color(0,0,0,100)));
        }
        RenderUtils.drawRoundRect(x,y,x + width(),y + height(),4,new Color(0,0,0,100));
        Minecraft.fontRendererObj.drawCenteredString(method.get(),x + width() / 2f,y + (background.isToggle() ?OFFSET :0) ,-1);
    }

    public int height() {
        return ( Minecraft.fontRendererObj.FONT_HEIGHT + (background.isToggle() ? OFFSET * 2 : 0));
    }

    public int width() {
        return (Minecraft.fontRendererObj.getStringWidth(method.get()) + 40);
    }
}
