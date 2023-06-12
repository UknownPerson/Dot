package xyz.Dot.api;

import xyz.Dot.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public final class PluginManager {
    public static final File plugins = new File(Client.instance.customfilemanager.getDirectory(), "plugins/");
    public void init() {
        if (!plugins.exists()) {
            plugins.mkdirs();
        }

        for (File file : Objects.requireNonNull(plugins.listFiles())) {
            if (file.getName().endsWith(".jar")) {
                try {
                    final FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    new Plugin(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}