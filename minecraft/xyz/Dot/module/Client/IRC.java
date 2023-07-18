package xyz.Dot.module.Client;

import net.minecraft.util.ChatComponentText;
import org.apache.commons.codec.binary.Base64;
import org.lwjgl.input.Keyboard;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.module.ModuleManager;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Notification;
import xyz.Dot.utils.Helper;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.UserUtils;
import xyz.Dot.utils.WebUtils;

import java.io.*;
import java.net.Socket;

public class IRC extends Module {
    public static Setting autorec = new Setting(ModuleManager.getModuleByName("IRC"), "AutoReconnect", true);
    public static Socket sck;
    public boolean run = false;
    public static boolean canrun = true;
    public static BufferedInputStream in;
    String host;
    int port;

    public IRC() {
        super("IRC", Keyboard.KEY_NONE, Category.Client);
        this.addValues(autorec);
        String get = WebUtils.get("https://gitee.com/UknownPerson/dot-login-check/raw/master/irc");
        if(get.equals("null")){
            canrun = false;
        }else{
            host = getSubString(get,"-host-","-host-");
            port = Integer.parseInt(getSubString(get,"-port-","-port-"));
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (UserUtils.name == null) {
            Notification.sendClientMessage(Translator.getInstance().m("You don't hava name. Ask Dot dev to get registered."), Notification.Type.WARNING);
        } else {
            if(canrun){

                new Thread(() -> {
                    try {
                        sck = new Socket(host, port);
                        in = new BufferedInputStream(sck.getInputStream());
                        run = true;

                        new Thread(() -> {
                            while(run){
                                try {
                                    Thread.sleep(5000);
                                    if(isServerClose(sck)){
                                        if(autorec.isToggle()){
                                            sck = new Socket(host, port);
                                            String login = "~user~" + UserUtils.getName() + "~user~";
                                            byte[] bstream = login.getBytes("GBK");
                                            OutputStream os = sck.getOutputStream();
                                            os.write(bstream);
                                        }else{
                                            this.setToggle(false);
                                            run =false;
                                        }
                                    }
                                } catch (InterruptedException | IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }).start();

                        String login = "~user~" + UserUtils.getName() + "~user~";
                        byte[] bstream = login.getBytes("GBK");
                        OutputStream os = sck.getOutputStream();
                        os.write(bstream);

                        while (run) {
                            byte[] bstream1 = new byte[1024];
                            int res = in.read(bstream1);
                            String s = new String(bstream1, "GBK");
                            s = s.substring(0,res);
                            Helper.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a7cIRC \u00a7f" + s));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }
    @Override
    public void onDisable() {
        if(canrun){
            new Thread(() -> {
                if (UserUtils.name != null) {
                    run = false;
                    try {
                        sck.close();
                        in.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if (UserUtils.name == null) {
            this.setToggle(false);
        }
    }

    public static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public Boolean isServerClose(Socket socket){
        try{
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        }catch(Exception se){
            return true;
        }
    }
}
