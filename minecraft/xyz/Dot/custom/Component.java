package xyz.Dot.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xyz.Dot.Client;
import xyz.Dot.module.Category;
import xyz.Dot.module.Client.CustomColor;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.RenderUtils;

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
        ModuleManager.getModules().add(module);
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

            if(Math.abs(x - getPosX()) > 3){
                adjustX = false;
            }

            if(Math.abs(y - getPosY()) > 3){
                adjustY = false;
            }

            if(check(x,1)){
                if(!adjustX){
                    setPosX(x);
                }

            }
            if(check(1,y)){
                if(!adjustY){
                    setPosY(y);
                }
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
        for(Component object : Client.instance.componentManager.components){
            if(!object.module.isToggle() || object == this)
                continue;
            if(Math.abs(object.getPosX() - this.getPosX()) < 4){
                if(drag){
                    this.setPosX((int) object.getPosX());
                    RenderUtils.drawRectWH(getPosX(),0,1,new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(),CustomColor.getColor().getRGB());
                    adjustX = true;
                }
            }
            if(Math.abs(object.getPosY() - this.getPosY()) < 4){
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
