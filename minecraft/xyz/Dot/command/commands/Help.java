package xyz.Dot.command.commands;

import xyz.Dot.command.Command;
import xyz.Dot.utils.Helper;

public class Help
        extends Command {
    public Help() {
        super("Help", new String[]{"help"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        String bind = "\u00a77.bind <module> <key>";
        String toggle = "\u00a77.t <module>";
        Helper.sendMessageWithoutPrefix(bind);
        Helper.sendMessageWithoutPrefix(toggle);
        return null;
    }
}

