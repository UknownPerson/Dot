/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.command.commands;

import net.minecraft.network.play.client.C01PacketChatMessage;
import xyz.Dot.command.Command;
import xyz.Dot.ui.Notification;

import static xyz.Dot.utils.Helper.mc;

public class Say
        extends Command {
    public Say() {
        super("say", new String[]{"say"}, "", "Say message.");
    }

    @Override
    public String execute(String[] args) {
        if (args.length > 0) {
            mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(args[0]));
        } else {
            Notification.sendClientMessage(".say <message>", Notification.Type.INFO);
        }
        return null;
    }
}

