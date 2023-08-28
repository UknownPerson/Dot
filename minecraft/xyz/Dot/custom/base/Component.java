package xyz.Dot.custom.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xyz.Dot.Client;
import xyz.Dot.module.Category;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Client.HUD;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.FontLoaders;
import xyz.Dot.utils.RenderUtils;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.shader.ShaderManager;

import java.awt.*;
import java.lang.reflect.Field;

public abstract class Component extends Gui {
    public Module module;

    protected Minecraft mc =Minecraft.getMinecraft();

    public Setting xValue;
    public Setting yValue;
    public int width;
    public int height;
    public String name;
    private float dragX, dragY;
    private boolean adjustX = false, adjustY = false;
    private boolean drag = false;
    public Component(int width, int height, String name) {
        this.height = height;
        this.width = width;
        this.name = name;
        module = new Module(name, Keyboard.KEY_NONE, Category.Render);
        Client.instance.getModuleManager().getModules().add(module);
        for (final Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                final Object obj = field.get(this);
                if (obj instanceof Setting) module.getValues().add((Setting) obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        xValue = new Setting(this.module,"X",0D,0D, (double) Minecraft.getMinecraft().displayWidth,1D);
        yValue = new Setting(this.module,"Y",0D,0D, (double) Minecraft.getMinecraft().displayHeight,1D);
        module.getValues().add(xValue);
        module.getValues().add(yValue);
    }

    public boolean doDraw() {
        return true;
    }

    public String getTitle() {
        return name;
    }

    public float getPosX() {
        return xValue.getDouble().floatValue();
    }
    public void addValues(Setting... values) {
        Setting[] s = values;
        int l = values.length;
        for (int i = 0; i < l; ++i) {
            Setting value = s[i];
            this.module.getValues().add(value);
        }
    }

    protected void drawBackground(int StartX,int StartY,String name){
        int finalStartY = StartY;
        int finalStartY1 = StartY;
        ShaderManager.addBlurTask(() -> RenderUtils.drawRoundRect(StartX, finalStartY, StartX + width, finalStartY1 + height,4, new Color(0, 0, 0, 64)));
        RenderUtils.drawRoundRect(StartX, finalStartY, StartX + width, finalStartY1 + height,4, new Color(0, 0, 0, 64));
        RenderUtils.drawHalfRoundRect(StartX, StartY, StartX + width, StartY + 12, 4,  HUD.transparent.isToggle() ? new Color(0,0,0,100) : CustomColor.getColor());
        FontLoaders.normalfont16.drawString(Translator.getInstance().m(name), (int) StartX + 5, (int) StartY + 4, new Color(255, 255, 255).getRGB());
    }

    public float getPosY() {
        return yValue.getDouble().floatValue();
    }

    public void setPosX(int posX) {
        xValue.setCurrentValue((double) posX);
    }

    public boolean canDraw (){
        return true;
    }


    public void setPosY(int posY) {
        yValue.setCurrentValue((double) posY);
    }

    public abstract void drawHUD(float x, float y, float partialTicks);

    public void doDrag(int mouseX, int mouseY) {
        xValue.setMaxValue(RenderUtils.width());
        yValue.setMaxValue(RenderUtils.height());
        if (this.drag && module.isToggle()) {
            if (!Mouse.isButtonDown(0)) {
                this.drag = false;
            }
            int x = (int) (mouseX - this.dragX);
            int y = (int) (mouseY - this.dragY);

            if(Client.instance.getComponentManager().dragging == null || Client.instance.getComponentManager().dragging == this) {
                Client.instance.getComponentManager().dragging = this;
                if (Math.abs(x - getPosX()) > 3) {
                    adjustX = false;
                }

                if (Math.abs(y - getPosY()) > 3) {
                    adjustY = false;
                }

                if (check(x, 1)) {
                    if (!adjustX) {
                        setPosX(x);
                    }

                }
                if (check(1, y)) {
                    if (!adjustY) {
                        setPosY(y);
                    }
                }
            }
        }else {
            if(Client.instance.getComponentManager().dragging == this){
                Client.instance.getComponentManager().dragging =null;
            }
        }
    }

    private boolean check(int x,int y){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return x > 0 && y > 0 && x + width < sr.getScaledWidth() && y + height < sr.getScaledHeight();
    }

    public void mouseClick(int mouseX, int mouseY, int button) {
        if (mouseX > getPosX() - 2 && mouseX < getPosX() + getWidth() && mouseY >getPosY() - 2 && mouseY < getPosY() + height && module.isToggle()) {
            if (button == 1) {
                module.toggle();
            }
            if (button == 0) {
                this.drag = true;
                this.dragX = mouseX - getPosX();
                this.dragY = mouseY - getPosY();
            }
        }
    }

    public void draw(float partialTicks) {

        if (! doDraw() || ! module.isToggle() || ! canDraw())
            return;

        ScaledResolution sr = new ScaledResolution(mc);
        if(Math.abs(sr.getScaledWidth() / 2f - (this.getPosX() + (width / 2f))) < 3) {
            if(drag){
                adjustX = true;
                this.setPosX((int) (sr.getScaledWidth() / 2f - width / 2f));
                RenderUtils.drawRectWH(sr.getScaledWidth() / 2f, 0, 1, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), CustomColor.getColor().getRGB());

            }
        }
        if(Math.abs(sr.getScaledHeight() / 2f - (this.getPosY() + (height / 2f))) < 3) {
            if(drag){
                adjustY = true;
                this.setPosY((int) (sr.getScaledHeight() / 2f - height / 2f));
                RenderUtils.drawRectWH(0,sr.getScaledHeight() / 2f,new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(),1, CustomColor.getColor().getRGB());
            }
        }
        for(Component object : Client.instance.getComponentManager().components){
            if(!object.module.isToggle() || object == this)
                continue;
            if(Math.abs(object.getPosX() - this.getPosX()) < 3){
                if(drag){
                    this.setPosX((int) object.getPosX());
                    RenderUtils.drawRectWH(getPosX(),0,1,new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(),CustomColor.getColor().getRGB());
                    adjustX = true;
                }
            }
            if(Math.abs(object.getPosY() - this.getPosY()) < 3){
                if(drag){
                    this.setPosY((int) object.getPosY());
                    RenderUtils.drawRectWH(0,getPosY(),new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(),1, CustomColor.getColor().getRGB());
                    adjustY = true;
                }
            }
        }

        drawHUD(getPosX(), getPosY(), partialTicks);
    }

    public int getHeight(){return  height;}

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    protected void setHeight (int y) {
        this.height = y;
    }
}
