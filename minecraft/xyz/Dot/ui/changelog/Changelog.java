package xyz.Dot.ui.changelog;

import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class Changelog {
    private final List<String> updates;

    private final String title;

    public String getTitle() {
        return title;
    }

    public List<String> getUpdates() {
        return updates;
    }

    public Changelog(String title){
        this.updates = new ArrayList<>();
        this.title = title;
    }

    public void addUpdate(Type type,String update){
        String suffix = "";
        switch (type){
            case Add:
                suffix = EnumChatFormatting.GREEN + "+ ";
                break;
            case Update:
                suffix = EnumChatFormatting.AQUA + "* ";
                break;
            case Remove:
                suffix = EnumChatFormatting.RED + "- ";
        }
        updates.add(suffix + update);
    }

    public enum Type{
        Add,
        Update,
        Remove
    }
}
