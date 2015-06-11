package com.ctrip.zeus;

/**
 * Created by zhoumy on 2015/6/10.
 */
public class JsonWriter {
    public static String write(Object object) {
        return String.format("%#.3s", object).replace("\\/", "/");
    }

    public static String writeCompact(Object object) {
        return String.format("%#s", object).replace("\\/", "/");
    }
}