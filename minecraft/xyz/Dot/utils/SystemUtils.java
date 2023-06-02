package xyz.Dot.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SystemUtils {

    public static String getHWID(){
        Scanner sc = null;
        try {
            // linux，windows命令
            String[] linux = {"dmidecode", "-t", "processor", "|", "grep", "'ID'"};
            String[] windows = {"wmic", "cpu", "get", "ProcessorId"};

            // 获取系统信息
            String property = System.getProperty("os.name");
            boolean iswindows = property.toLowerCase().contains("windows");
            if(!iswindows){
                String hashed = "null";
                String s = InetAddress.getLocalHost().getHostName();
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(s.getBytes());
                    hashed = new BigInteger(1, md.digest()).toString(16);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return hashed;
            }
            Process process = Runtime.getRuntime().exec(property.contains("Window") ? windows : linux);
            process.getOutputStream().close();
            sc = new Scanner(process.getInputStream(), "utf-8");
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }

        if (sc != null) {
            String hashed = "null";
            String s = sc.next();
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(s.getBytes());
                hashed = new BigInteger(1, md.digest()).toString(16);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return hashed;
        }else{
            return "null";
        }
    }
}
