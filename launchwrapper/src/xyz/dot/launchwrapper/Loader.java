package xyz.dot.launchwrapper;

import xyz.dot.launchwrapper.util.DialogDownload;
import xyz.dot.launchwrapper.util.HttpUtil;
import xyz.dot.launchwrapper.util.JsonUtil;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Loader {
    private static DialogDownload downloadFrame;

    public static void main(String[] args) throws Exception {
        String hashURL = "http://czf_233.gitee.io/dot/assets/hash.json";
        String fileURL = "http://czf_233.gitee.io/dot/assets/client.jar";

        JsonUtil hash = new JsonUtil(HttpUtil.getFromURL(new URL(hashURL)));
        Map<String, Object> jsonObj = (Map<String, Object>) hash.parse();
        System.out.println(jsonObj.get("hash"));

        boolean hashesMatch = jsonObj.get("hash").equals(calculateSHA1(new File("dot.bin")));

        if (hashesMatch) {
            System.out.println("Hashes match.");
        } else {
            System.out.println("Hashes do not match. Downloading file...");
            new File("dot.bin").createNewFile();
            downloadFrame = new DialogDownload(null);
            Thread thread = new Thread(() -> {
                while (true){
                    downloadFrame.setText("Updating...");
                    downloadFrame.getProgressBar().setValue((int) ((double) new File("dot.bin").length() / (1024 * 1024)));
                }
            });
            thread.setDaemon(true);
            thread.start();

            downloadFrame.setResizable(false);
            downloadFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            downloadFrame.setVisible(true);
            downloadFrame.getProgressBar().setValue(0);
            downloadFrame.getProgressBar().setMaximum(7);
            FileChannel fileChannel = FileChannel.open(new File("dot.bin").toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            fileChannel.transferFrom(Channels.newChannel(new URL(fileURL).openConnection().getInputStream()), 0L, Long.MAX_VALUE);
            downloadFrame.setVisible(false);
            thread.stop();

        }
        URL[] urls = new URL[]{new File("dot.bin").toURL()};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class<?> mainClass = classLoader.loadClass("net.minecraft.client.main.Main");
        mainClass.getMethod("main", String[].class).invoke(null, (Object) args);
    }

    public static String calculateSHA1(File file) {
        try {
            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    sha1Digest.update(buffer, 0, bytesRead);
                }
            }

            byte[] hashBytes = sha1Digest.digest();
            StringBuilder hexBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexBuilder.append('0');
                }
                hexBuilder.append(hex);
            }
            return hexBuilder.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
