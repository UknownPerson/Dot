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
import xyz.Dot.utils.Helper;

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
                Helper.sendMessageWithoutPrefix(String.format("Successful bind %s to %s", arrobject));
            } else {
                Helper.sendMessageWithoutPrefix("Bind unsuccessful, please check spelling.");
            }
        } else {
            Helper.sendMessageWithoutPrefix("\u00a77.bind <module> <key>");
        }
        return null;
    }
}

