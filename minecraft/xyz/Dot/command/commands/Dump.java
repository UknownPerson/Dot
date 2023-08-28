package xyz.Dot.command.commands;

import xyz.Dot.Client;
import xyz.Dot.command.Command;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;
import xyz.Dot.utils.Translator;

public class Dump extends Command {
    public Dump() {
        super("Dump", new String[]{"dump"}, "", "Dump");
    }

    @Override
    public String execute(String[] var1) {
        for(Module module : Client.instance.getModuleManager().getModules()){
            for(Setting setting : module.getValues()) {
                if(setting.getName().length() == 1)
                    continue;
                Translator.getInstance().m(setting.getName());
            }
        }
        return "";
    }
}
