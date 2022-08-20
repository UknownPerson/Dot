package xyz.Dot.module.Misc;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;

public class Teams extends Module {
    public Teams() {
        super("Teams", Keyboard.KEY_NONE, Category.Misc);
    }


    public static boolean isOnSameTeam(Entity entity) {
        if (!ModuleManager.getModuleByName("Teams").isToggle()) return false;
        if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("\247")) {
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2
                    || entity.getDisplayName().getUnformattedText().length() <= 2) {
                return false;
            }
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        }
        return false;
    }

}
