/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package xyz.Dot.command.commands;

import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.command.Command;
import xyz.Dot.module.Module;
import xyz.Dot.ui.Notification;
import xyz.Dot.utils.Translator;

public class Bind
extends Command {
    public Bind() {
        super("Bind", new String[]{"b"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        if (args.length >= 2) {
            Module m = Client.instance.getModuleManager().getModuleByName(args[0]);
            if (m != null) {
                int k = Keyboard.getKeyIndex((String)args[1].toUpperCase());
                m.setKeyBind(k);
                Object[] arrobject = new Object[2];
                arrobject[0] = m.getName();
                arrobject[1] = k == 0 ? "none" : args[1].toUpperCase();
                Notification.sendClientMessage(String.format(Translator.getInstance().m("Successful bind {} to {}", arrobject)), Notification.Type.SUCCESS);
            } else {
                Notification.sendClientMessage(Translator.getInstance().m("Bind unsuccessful, please check spelling."), Notification.Type.WARNING);
            }
        } else {
            Notification.sendClientMessage(".bind <module> <key>", Notification.Type.INFO);
        }
        return null;
    }
}

