package xyz.Dot.command.commands;

import xyz.Dot.command.Command;
import xyz.Dot.ui.Notification;

public class Help
        extends Command {
    public Help() {
        super("Help", new String[]{"help"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        String bind = ".bind <module> <key>";
        String say = ".say <message>";
        String toggle = ".t <module>";
        Notification.sendClientMessage(toggle, Notification.Type.INFO);
        Notification.sendClientMessage(say, Notification.Type.INFO);
        Notification.sendClientMessage(bind, Notification.Type.INFO);
        return null;
    }
}

