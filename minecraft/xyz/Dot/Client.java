package xyz.Dot;

import org.lwjgl.opengl.Display;
import xyz.Dot.Log.Log_Dot;

public enum Client {
    instance;
    public String client_name = "Dot";
    public String client_version = "0.1";
    public boolean inDevelopment = true;

    public void run(){

        Log_Dot.info("客户端启动");
        String title = client_name + " " + client_version + " " + getMode() + "- Minecraft 1.8.8";
        Display.setTitle(title);

    }

    public void stop(){

        Log_Dot.info("客户端关闭");
        Log_Dot.sava_Log();

    }

    public String getMode(){

        if(inDevelopment){
            return "Dev ";
        }else{
            return "";
        }

    }
}
