
package xyz.Dot.module.Misc;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.misc.EventKey;
import xyz.Dot.event.events.rendering.EventRender3D;
import xyz.Dot.event.events.world.EventPacketRecieve;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.TimerUtil;

public final class FreeLook extends Module {

    public Setting invertPitch = new Setting(this,"Invert Pitch",  false);

    private int previousPerspective;
    public float originalYaw, originalPitch, lastYaw, lastPitch;
    public static boolean keyDown;
    public FreeLook() {
        super("FreeLook", Keyboard.KEY_NONE,Category.Misc);
        addValues(invertPitch);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        mc.thePlayer.rotationYaw = originalYaw;
        mc.thePlayer.rotationPitch = originalPitch;
        mc.gameSettings.showDebugInfo = previousPerspective;
    }

    @EventHandler
    public void renderHud(EventRender3D event) {

        if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)){
            if(!keyDown){
                keyDown = true;
                try {
                    previousPerspective = mc.gameSettings.showDebugInfo;
                    originalYaw = lastYaw = mc.thePlayer.rotationYaw;
                    originalPitch = lastPitch = mc.thePlayer.rotationPitch;

                    if (invertPitch.isToggle()) lastPitch *= -1;
                }catch (Exception e1){

                }
            }

        }else{

            if(keyDown){
                keyDown = false;

                mc.thePlayer.rotationYaw = originalYaw;
                mc.thePlayer.rotationPitch = originalPitch;
                mc.gameSettings.showDebugInfo = previousPerspective;

            }

        }

        if(keyDown){
            mc.mouseHelper.mouseXYChange();
            final float f =mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            final float f1 = (float) (f * f * f * 1.5);
            lastYaw +=mc.mouseHelper.deltaX * f1;
            lastPitch -=mc.mouseHelper.deltaY * f1;

            lastPitch = MathHelper.clamp_float(lastPitch, -90, 90);
            mc.gameSettings.showDebugInfo = 1;
        }

    }

    @EventHandler
    public void renderHud(EventPacketRecieve event) {

        if(keyDown){
            if(event.getPacket() instanceof S08PacketPlayerPosLook){
                originalYaw = ((S08PacketPlayerPosLook) event.getPacket()).getYaw();
                originalPitch = ((S08PacketPlayerPosLook) event.getPacket()).getPitch();
            }
        }

    }
}