/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.command.commands;

import net.minecraft.util.EnumChatFormatting;
import xyz.Dot.Client;
import xyz.Dot.command.Command;
import xyz.Dot.module.Module;
import xyz.Dot.utils.Helper;

import java.util.concurrent.TimeUnit;

public class Toggle
extends Command {
    public Toggle() {
        super("t", new String[]{"toggle", "togl", "turnon", "enable"}, "", "Toggles a specified Module");
    }

    @Override
    public String execute(String[] args) {
        String modName = "";
        if (args.length > 1) {
            modName = args[1];
        } else if (args.length < 1) {
            Helper.sendMessageWithoutPrefix("\u00a77.t <module>");
        }
        boolean found = false;
        Module m = Client.instance.getModuleManager().getModuleByName(args[0]);
        if (m != null) {
            m.toggle();
            found = true;
            if (m.isToggle()) {
                Helper.sendMessageWithoutPrefix(m.getName() + (Object)((Object)EnumChatFormatting.GRAY) + " was" + (Object)((Object)EnumChatFormatting.GREEN) + " enabled");
            } else {
                Helper.sendMessageWithoutPrefix(m.getName() + (Object)((Object)EnumChatFormatting.GRAY) + " was" + (Object)((Object)EnumChatFormatting.RED) + " disabled");
            }
        }
        if (!found) {
            Helper.sendMessageWithoutPrefix("Module name " + (Object)((Object)EnumChatFormatting.RED) + args[0] + (Object)((Object)EnumChatFormatting.GRAY) + " not found");
        }
        return null;
    }
}

