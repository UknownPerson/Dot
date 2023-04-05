package xyz.Dot.file.files;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import xyz.Dot.Client;
import xyz.Dot.file.CustomFile;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModulesFile extends CustomFile {

    private final File directory = new File(Minecraft.getMinecraft().mcDataDir.toString() + "/" + Client.instance.client_name + "/" + "Modules");

    public ModulesFile(Gson gson, File file) {
        super(gson, file);
    }

    @Override
    public void loadFile() throws IOException {

        makeDirecotry();


        for (Module module : ModuleManager.getModules()) {

            makeModuleFile(module);

            FileReader fr = new FileReader(getFile(module));

            JsonObject jsonObject = getGson().fromJson(fr, JsonObject.class);

            if (jsonObject == null) {
                fr.close();
                continue;
            }


            if (jsonObject.has("toggled"))
                if (Boolean.parseBoolean(jsonObject.get("toggled").getAsString()))
                    module.toggle();


            if (jsonObject.has("key"))
                module.setKeyBind(Integer.parseInt(jsonObject.get("key").getAsString()));


            List<Setting> settings = module.getValues();

            if (settings != null && jsonObject.has("settings")) {

                JsonArray jsonArray = (JsonArray) jsonObject.get("settings");

                jsonArray.forEach(jsonElement -> settings.stream().filter(setting -> jsonElement.getAsJsonObject().has(setting.getName()))
                        .forEach(setting -> {
                            if (setting.isBoolean()) {
                                setting.setToggle(jsonElement.getAsJsonObject().get(setting.getName()).getAsBoolean());
                            } else if (setting.isValue()) {
                                setting.setCurrentValue(jsonElement.getAsJsonObject().get(setting.getName()).getAsDouble());
                            } else {
                                setting.setCurrentMode(jsonElement.getAsJsonObject().get(setting.getName()).getAsString());
                            }

                        }));
            }


            fr.close();


        }


    }

    @Override
    public void saveFile() throws IOException {
        makeDirecotry();

        for (Module module : ModuleManager.getModules()) {

            makeModuleFile(module);

            FileWriter fw = new FileWriter(getFile(module));


            JsonObject jsonObject = new JsonObject();

            if(module.getName().equals("ClickGui")){
                jsonObject.addProperty("toggled", false);
            }else{
                jsonObject.addProperty("toggled", module.isToggle());
            }

            jsonObject.addProperty("key", module.getKeyBind());


            List<Setting> settings = module.getValues();
            if (settings != null) {

                JsonArray jsonArray = new JsonArray();
                JsonObject jsonObject1 = new JsonObject();

                settings.forEach(setting -> {
                    if (setting.isBoolean())
                        jsonObject1.addProperty(setting.getName(), setting.isToggle());
                    else if (setting.isValue())
                        jsonObject1.addProperty(setting.getName(), setting.getCurrentValue());
                    else
                        jsonObject1.addProperty(setting.getName(), setting.getCurrentMode());
                });

                jsonArray.add(jsonObject1);
                jsonObject.add("settings", jsonArray);

            }

            fw.write(getGson().toJson(jsonObject));
            fw.close();

        }


    }

    private void makeDirecotry() {
        if (!directory.exists())
            directory.mkdir();
    }

    private void makeModuleFile(Module module) throws IOException {
        if (!getFile(module).exists())
            getFile(module).createNewFile();
    }


    private File getFile(Module module) {
        return new File(directory, module.getName() + ".json");
    }


}
