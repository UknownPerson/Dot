/*
 * Decompiled with CFR 0_132.
 */
package xyz.Dot.command;

import xyz.Dot.Client;
import xyz.Dot.command.commands.Bind;
import xyz.Dot.command.commands.Dump;
import xyz.Dot.command.commands.Help;
import xyz.Dot.command.commands.Toggle;
import xyz.Dot.event.EventBus;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.misc.EventChat;
import xyz.Dot.ui.Notification;
import xyz.Dot.utils.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandManager{
    private List<Command> commands;

    public void run() {
        this.commands = new ArrayList<Command>();
        this.commands.add(new Command("test", new String[]{"test"}, "", "testing") {

            @Override
            public String execute(String[] args) {
                for (Command command : CommandManager.this.commands) {
                }
                return null;
            }
        });
        this.commands.add(new Bind());
        this.commands.add(new Toggle());
        //this.commands.add(new Say());
        this.commands.add(new Help());
        if(Client.instance.inDevelopment)
            this.commands.add(new Dump());
        EventBus.getInstance().register(this);
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    public Optional<Command> getCommandByName(String name) {
        return this.commands.stream().filter(c2 -> {
            boolean isAlias = false;
            String[] arrstring = c2.getAlias();
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String str = arrstring[n2];
                if (str.equalsIgnoreCase(name)) {
                    isAlias = true;
                    break;
                }
                ++n2;
            }
            if (!c2.getName().equalsIgnoreCase(name) && !isAlias) {
                return false;
            }
            return true;
        }).findFirst();
    }

    public void add(Command command) {
        this.commands.add(command);
    }

    @EventHandler
    private void onChat(EventChat e) {
        if (e.getMessage().length() > 1 && e.getMessage().startsWith(".")) {
            e.setCancelled(true);
            String[] args = e.getMessage().trim().substring(1).split(" ");
            Optional<Command> possibleCmd = this.getCommandByName(args[0]);
            if (possibleCmd.isPresent()) {
                String result = possibleCmd.get().execute(Arrays.copyOfRange(args, 1, args.length));
                if (result != null && !result.isEmpty()) {
                    Helper.sendMessage(result);
                }
            } else {
                Notification.sendClientMessage("Command not found.", Notification.Type.WARNING);
            }
        }
    }

}

