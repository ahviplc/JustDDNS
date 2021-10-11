package com.lc.ddns.util;

public class MyUtils {
    public static void sleep(Integer time){
        try {
            Thread.sleep(time);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
