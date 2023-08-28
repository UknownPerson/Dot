package xyz.Dot.module.Client;

import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;
import xyz.Dot.Client;
import xyz.Dot.event.EventHandler;
import xyz.Dot.event.events.rendering.EventRender2D;
import xyz.Dot.module.Category;
import xyz.Dot.module.Module;
import xyz.Dot.setting.Setting;
import xyz.Dot.ui.Notification;
import xyz.Dot.utils.Helper;
import xyz.Dot.utils.Translator;
import xyz.Dot.utils.UserUtils;
import xyz.Dot.utils.WebUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class IRC extends Module {
    public static Setting autorec = new Setting(Client.instance.getModuleManager().getModuleByName("IRC"), "AutoReconnect", true);
    public static Socket sck;
    public boolean run = false;
    public static boolean canrun = true;
    public static BufferedInputStream in;
    String host;
    int port;
    public static ArrayList<Thread> ts = new ArrayList<>();
    Thread t2fuck;

    public IRC() {
        super("IRC", Keyboard.KEY_NONE, Category.Client);
        this.addValues(autorec);
        String get = WebUtils.get("https://gitee.com/UknownPerson/dot-login-check/raw/master/irc");
        if (get.equals("null")) {
            canrun = false;
        } else {
            host = getSubString(get, "-host-", "-host-");
            port = Integer.parseInt(getSubString(get, "-port-", "-port-"));
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        /*
        for (Thread t : ts) {
            t.stop();
        }
        ts.clear();
         */

        if (UserUtils.name == null) {
            Notification.sendClientMessage(Translator.getInstance().m("You don't hava name. Ask Dot dev to get registered."), Notification.Type.WARNING);
        } else {
            if (canrun) {

                Thread t1 = new Thread(() -> {
                    try {
                        sck = new Socket(host, port);
                        in = new BufferedInputStream(sck.getInputStream());
                        run = true;

                        {
                            String login = "~user~" + UserUtils.getName() + "~user~";
                            byte[] bstream = login.getBytes("GBK");
                            OutputStream os = sck.getOutputStream();
                            os.write(bstream);
                        }

                        Thread t = new Thread(() -> {
                            while (run) {
                                byte[] bstream1 = new byte[1024];
                                int res;
                                String s;
                                try {
                                    res = in.read(bstream1);
                                    s = new String(bstream1, "GBK");
                                    if (res < 0) {
                                        run = false;
                                        sck.close();
                                        in.close();
                                        onEnable();
                                    } else {
                                        s = s.substring(0, res);
                                        System.out.println(res);
                                        Helper.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a7cIRC \u00a7f" + s));
                                    }

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                        t.start();
                        ts.add(t);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                t1.start();
                ts.add(t1);
            }
        }
    }

    @Override
    public void onDisable() {
        if (canrun) {

            Thread t2 = new Thread(() -> {
                if (UserUtils.name != null) {
                    run = false;
                    try {
                        Thread.sleep(10);
                        sck.close();
                        in.close();
                        for (Thread t : ts) {
                            if (t != t2fuck) {
                                t.stop();
                            }
                        }
                        ts.clear();
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            t2fuck = t2;
            //ts.add(t2);
            t2.start();
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
}
