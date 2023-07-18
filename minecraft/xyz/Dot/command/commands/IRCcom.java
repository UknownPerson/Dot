package xyz.Dot.command.commands;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import xyz.Dot.command.Command;
import xyz.Dot.module.Client.IRC;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.ui.Notification;
import xyz.Dot.utils.UserUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import static xyz.Dot.utils.Helper.mc;

public class IRCcom extends Command {
    public IRCcom() {
        super("IRC", new String[]{"irc"}, "", "Say message on irc.");
    }

    @Override
    public String execute(String[] args) {
        if (args.length > 0) {
            String message = null;
            int i = 0;
            for (String s : args) {
                if (i == 0) {
                    message = s;
                } else {
                    message = message + " " + s;
                }
                i++;
            }
            message = changeCharString0(message,'~');
            System.out.println(message);
            String finalMessage = message;
            if(IRC.canrun){
                new Thread(() -> {
                    if(ModuleManager.getModuleByName("IRC").isToggle()){
                        try {
                            String login = "~message~" + finalMessage + "~message~";
                            byte[] bstream= login.getBytes("GBK");
                            OutputStream os  = IRC.sck.getOutputStream();
                            os.write(bstream);
                        }catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }

        } else {
            Notification.sendClientMessage(".irc <message>", Notification.Type.INFO);
        }
        return null;
    }

    public String changeCharString0(String sourceString, char chElemData) {
        String deleteString = "";
        for (int i = 0; i < sourceString.length(); i++) {
            if (sourceString.charAt(i) != chElemData) {
                deleteString += sourceString.charAt(i);
            }
        }
        return deleteString;
    }
}
