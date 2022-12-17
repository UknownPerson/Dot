/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import xyz.Dot.Client;

public class Helper {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendMessageOLD(String msg) {
        Object[] arrobject = new Object[2];
        Client.instance.getClass();
        arrobject[0] = (Object)((Object)EnumChatFormatting.BLUE) + Client.instance.client_name + (Object)((Object)EnumChatFormatting.GRAY) + ": ";
        arrobject[1] = msg;
        Helper.mc.thePlayer.addChatMessage(new ChatComponentText(String.format("%s%s", arrobject)));
    }

    public static void sendMessage(String message) {
        new ChatUtils.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static void sendMessageWithoutPrefix(String message) {
        new ChatUtils.ChatMessageBuilder(false, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static boolean onServer(String server) {
        if (!mc.isSingleplayer() && Helper.mc.getCurrentServerData().serverIP.toLowerCase().contains(server)) {
            return true;
        }
        return false;
    }


}

