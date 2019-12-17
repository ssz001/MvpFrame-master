package utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 15:38
 * 对象的工具类
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

    public static  boolean isNull(@Nullable Object obj){
        return null == obj;
    }

    public static boolean nonNull(@Nullable Object obj) {
        return obj != null;
    }

    /**
     * 两个都为null 也返回true
     */
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

}
