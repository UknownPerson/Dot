package xyz.Dot.module;

import net.minecraft.client.Minecraft;
import xyz.Dot.event.EventBus;

public class Module {
    private String name;
    private int keyBind;
    private Category moduletype;

    private boolean toggle;
    protected Minecraft mc = Minecraft.getMinecraft();
    private float anim;

    public float getAnimX() {
        return this.anim;
    }

    public void setAnimX(float anim) {
        this.anim = anim;
    }

    public Module(String name, int keyBind, Category moduletype) {
        this.name = name;
        this.keyBind = keyBind;
        this.moduletype = moduletype;
        this.toggle = false;
    }

    public Module(String name, int keyBind, Category moduletype,boolean toggle) {
        this.name = name;
        this.keyBind = keyBind;
        this.moduletype = moduletype;
        this.toggle = toggle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public Category getModuletype() {
        return moduletype;
    }

    public void setModuletype(Category moduletype) {
        this.moduletype = moduletype;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        if(toggle){
            EventBus.getInstance().register(new Object[]{this});
        }else{
            EventBus.getInstance().unregister(new Object[]{this});
        }
        this.toggle = toggle;
    }

    public void toggle(){
        this.toggle = !this.toggle;
        if(this.toggle){
            onEnable();
            EventBus.getInstance().register(new Object[]{this});
        }else{
            EventBus.getInstance().unregister(new Object[]{this});
            onDisable();
        }
    }

    public void onEnable(){

    }

    public void onDisable(){

    }
}
