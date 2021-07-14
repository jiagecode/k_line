package com.line.backstage.utils;

public class StrUtils {

    public static String objToStr(Object object) {
        if (null == object) {
            return "";
        }
        return String.valueOf(object);
    }

}
