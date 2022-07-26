package xyz.Dot.setting;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import xyz.Dot.module.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingManager {

    private ArrayList<Setting> settings;

    public SettingManager(){
        this.settings = new ArrayList<Setting>();
    }

    public void addSetting(Setting s){
        this.settings.add(s);
    }

    public ArrayList<Setting> getSettingForModule(Module m){
        ArrayList<Setting> settings = new ArrayList<>();

        for(Setting setting : this.settings){
            if(setting.getModule().equals(m)){
                settings.add(setting);
            }
        }
        return settings;
    }

}
