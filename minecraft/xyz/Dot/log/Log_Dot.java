package xyz.Dot.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log_Dot {

    static String Log = "";
    static SimpleDateFormat formatter_print = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat formatter_output = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    static Date date = new Date(System.currentTimeMillis());

    public static void info(String text){

        custom("Info", text);

    }

    public static void warn(String text){

        custom("Warn", text);

    }

    public static void error(String text){

        custom("Error", text);

    }

    public static void custom(String head, String text){
        String log_a = "[" + formatter_print.format(date) + "] ";
        String log_b = "[" + head + "]: ";
        String log_c = text;
        String log_All = log_a + log_b + log_c;
        Log = (Log.isEmpty()?"":Log) + log_All + "\n";
        System.out.println(log_All);
    }

    public static void sava_Log(){
        String file_name = formatter_output.format(date) + "_DotClientLog";
    }

}
