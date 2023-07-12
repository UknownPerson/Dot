package xyz.Dot.module;

import net.minecraft.client.Minecraft;
import xyz.Dot.event.EventBus;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Notification;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private int keyBind;
    private Category moduletype;

    private boolean toggle;
    protected static Minecraft mc = Minecraft.getMinecraft();
    private float animy;
    private float cguired = 0;
    private float cguigreen = 0;

    public float getCguired() {
        return cguired;
    }

    public void setCguired(float cguired) {
        this.cguired = cguired;
    }

    public float getCguigreen() {
        return cguigreen;
    }

    public void setCguigreen(float cguigreen) {
        this.cguigreen = cguigreen;
    }

    public float getCguiblue() {
        return cguiblue;
    }

    public void setCguiblue(float cguiblue) {
        this.cguiblue = cguiblue;
    }

    private float cguiblue = 0;

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
    private ArrayList<Setting> values = new ArrayList<>();

    public void addValues(Setting... values) {
        Setting[] s = values;
        int l = values.length;
        for (int i = 0; i < l; ++i) {
            Setting value = s[i];
            this.values.add(value);
        }
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

    public Module(String name, int keyBind, Category moduletype, boolean toggle) {
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
        if (this.toggle == toggle) {
            return;
        }

        if (this.getModuletype() == Category.Cheat) {
            if (!UserUtils.SigmaMode) {
                Notification.sendClientMessage(Translator.getInstance().m("You are not a Sigma user.You can't enable it."), Notification.Type.WARNING);
                return;
            }
        }

        if (toggle) {
            EventBus.getInstance().register(new Object[]{this});
            onEnable();
            Notification.sendClientMessage(this.name + Translator.getInstance().m(" was enabled."), Notification.Type.SUCCESS);
        } else {
            EventBus.getInstance().unregister(new Object[]{this});
            onDisable();
            Notification.sendClientMessage(this.name + Translator.getInstance().m(" was disabled."), Notification.Type.ERROR);
        }
        this.toggle = toggle;
    }

    public void toggle() {
        setToggle(!this.toggle);
        //  BlockSourceImpl.worldObj.playSoundEffect(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, "random.click", 0.3F, isToggle() ? 0.6F : 0.5F);
    }

    public void onEnable() {

    }

    public void onDisable() {

    }
}
