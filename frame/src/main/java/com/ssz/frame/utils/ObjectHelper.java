package com.ssz.frame.utils;

import android.support.annotation.NonNull;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 15:38
 */
public final class ObjectHelper {

    private ObjectHelper(){
        // no instance
    }

    public static <T> void checkNotNull(T value){
        if (null == value){
            throw new NullPointerException();
        }
    }

    public static <T> void checkNotNull(T value,String msg){
        if (null == value){
            throw new NullPointerException(msg);
        }
    }

    public static <T> T requireNotNull(T value){
        if (null == value){
            throw new NullPointerException();
        }
        return value;
    }

    public static <T> T requireNotNull(T value,String msg){
        if (null == value){
            throw new NullPointerException(msg);
        }
        return value;
    }

    public static <T> T nullDefaultValue(T value,@NonNull T defValue){
        checkNotNull(defValue);
        if (null == value){
            return defValue;
        }
        return value;
    }

    public static  boolean isNull(Object value){
        return null == value;
    }

    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

}
