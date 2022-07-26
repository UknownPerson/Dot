/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.command.commands;

import net.minecraft.util.EnumChatFormatting;
import xyz.Dot.command.Command;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.utils.Helper;

public class Cheats
extends Command {
    public Cheats() {
        super("Cheats", new String[]{"mods"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        if (args.length == 0) {
            StringBuilder list = new StringBuilder(String.valueOf(ModuleManager.getModules().size()) + " Cheats - ");
            for (Module cheat : ModuleManager.getModules()) {
                list.append((Object)(cheat.isToggle() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED)).append(cheat.getName()).append(", ");
            }
            Helper.sendMessageWithoutPrefix(list.toString().substring(0, list.toString().length() - 2));
        } else {
            Helper.sendMessageWithoutPrefix("> Correct usage .cheats");
        }
        return null;
    }
}

