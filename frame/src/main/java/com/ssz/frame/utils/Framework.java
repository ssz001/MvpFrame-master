package com.ssz.frame.utils;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * @author zsp
 * create at 2019/1/18 17:04
 * 持有 ApplicationContext 的类，方便获取ApplicationContext
 * 因为框架很多都嵌入了Application content，这里解耦一下
 */
public class Framework {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    /**
     * Application onCreate()里初始化，获取静态ApplicationContext
     * 便于获取Application
     * @param context ApplicationContext
     */
    public static void init(Context context){
        Framework.context = context;
    }

    /**
     * 获取本项目ApplicationContext对象，类型转换
     */
    public static <T> T toApp(Class<T> clz){
        return ObjectHelper.requireNotNull ((T)Framework.context,"Framework.context no init !");
    }

}
