package xyz.Dot.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import xyz.Dot.Client;
import xyz.Dot.file.files.ModulesFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomFileManager {

    private ArrayList<CustomFile> files;
    private Gson gson;
    private File directory;

    public CustomFileManager() {
        files = new ArrayList<CustomFile>();
        gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        directory = new File(Minecraft.getMinecraft().mcDataDir.toString() + "/" + Client.instance.client_name);
        makeDirectory();
        registerFiles();
    }

    private void makeDirectory() {
        if (!directory.exists())
            directory.mkdir();
    }

    private void registerFiles() {
        files.add(new ModulesFile(gson, null));
    }


    public void loadFiles() {
        for (CustomFile file : files) {
            try {
                file.loadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void saveFiles() {

        for (CustomFile file : files) {
            try {
                file.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
