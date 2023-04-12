package com.humphrey.c196.Utility;

public class Util {
    public static void waitAsec(){
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
