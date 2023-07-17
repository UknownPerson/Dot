package xyz.Dot.command.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import xyz.Dot.command.Command;
import xyz.Dot.ui.Notification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Esu extends Command {

    public static final String API = "https://zy.xywlapi.cc/qqapi?qq=";


    public Esu() {
        super("esu", new String[]{"esu"}, "", "esu command here.");
    }

    @Override
    public String execute(String[] var114514) {
        if (var114514.length >= 2) {
            String opt = var114514[0], fuck = var114514[1];
            Runnable fuckk = null;
            if (Objects.equals(opt, "qq")) {
                fuckk = () -> {
                    try {
                        URL fuck0 = new URL(API + fuck);
                        HttpURLConnection shit = (HttpURLConnection) fuck0.openConnection();
                        shit.setRequestMethod("GET");

                        BufferedReader in = new BufferedReader(new InputStreamReader(shit.getInputStream()));
                        String fk;
                        StringBuilder sb = new StringBuilder();
                        while ((fk = in.readLine()) != null) {
                            sb.append(fk);
                        }
                        in.close();
                        Gson g = new Gson();
                        JsonObject fcuk2 = g.fromJson(sb.toString(), JsonObject.class);

                        String var3 = fcuk2.get("phone").getAsString();
                        String var5 = fcuk2.get("phonediqu").getAsString();

                        Notification.sendClientMessage("QQ:" + fuck + " " + "Phone:" + var3 + " " + "phonediqu:" + var5 + "\n" + "AoneHax(李铭舒)户籍:\n实名:李铭舒\n母亲电话号:18910800763", Notification.Type.INFO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            } else if (Objects.equals(opt, "phone")) {
                // some inquire about phone number
            }
            Thread thread = new Thread(fuckk);
            thread.start();
        } else {
            Notification.sendClientMessage(".esu <qq/phone> <QQNumber/PhoneNumber>", Notification.Type.INFO);
        }
        return null;
    }
}
