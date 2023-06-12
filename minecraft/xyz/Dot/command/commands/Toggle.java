/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.command.commands;

import xyz.Dot.Client;
import xyz.Dot.command.Command;
import xyz.Dot.module.Module;
import xyz.Dot.ui.Notification;
import xyz.Dot.utils.Translator;

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
            Notification.sendClientMessage(".t <module>", Notification.Type.INFO);
        }
        boolean found = false;
        Module m = Client.instance.getModuleManager().getModuleByName(args[0]);
        if (m != null) {
            m.toggle();
            found = true;
        }
        if (!found) {
            Notification.sendClientMessage(Translator.getInstance().m("Module name {} not found.",args[0]), Notification.Type.WARNING);
        }
        return null;
    }
}

