package xyz.Dot.utils;

import net.minecraft.util.ResourceLocation;
import xyz.Dot.Client;
import xyz.Dot.ui.ImageLoader;

public class UserUtils {
    public static String name = null;
    public static String prefix = null;
    public static boolean SigmaMode = false;
    public static double ver = Client.instance.client_version;

    public static boolean isLoadOK() {
        return loadOK;
    }

    public static void setLoadOK(boolean loadOK) {
        UserUtils.loadOK = loadOK;
    }

    public static boolean loadOK = false;

    public static ResourceLocation getUserProfilePhoto(){
        if(name == null){
            return ImageLoader.tohru0round;
        }else{
            return ImageLoader.tohru0round;
        }
    }

    public static String getName() {
        return name;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getALLPrefix() {
        String s;
        if (name != null) {
            s = "[" + prefix + "]";
        }else{
            s = "";
        }
        return s;
    }
}
