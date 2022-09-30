package xyz.Dot.module;

import net.minecraft.client.Minecraft;
import xyz.Dot.event.EventBus;
import xyz.Dot.setting.Setting;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private int keyBind;
    private Category moduletype;

    private boolean toggle;
    protected Minecraft mc = Minecraft.getMinecraft();
    private float animy;

    public float getColoranim() {
        return coloranim;
    }

    public void setColoranim(float coloranim) {
        this.coloranim = coloranim;
    }

    float coloranim = 175;

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    float partialTicks;
    static ArrayList<Setting> values = new ArrayList<>();

    protected void addValues(Setting value) {
        values.add(value);
    }

    public List<Setting> getValues() {
        return this.values;
    }

    public float getAnimY() {
        return this.animy;
    }

    public void setAnimY(float animy) {
        this.animy = animy;
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
        return this.name;
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
            EventBus.getInstance().register(new Object[]{this});
            onEnable();
        }else{
            EventBus.getInstance().unregister(new Object[]{this});
            onDisable();
        }

      //  BlockSourceImpl.worldObj.playSoundEffect(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, "random.click", 0.3F, isToggle() ? 0.6F : 0.5F);

    }

    public void onEnable(){

    }

    public void onDisable(){

    }
}
