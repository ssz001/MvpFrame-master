package com.ssz.other;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : zsp
 * time : 2019 10 2019/10/11 9:25
 * 这里用注解类代替了枚举类 ,利用 @StringDef()代替枚举，省内存
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({NetType.AUTO,NetType.WIFI,NetType.CMNET,NetType.CMWAP,NetType.NONE})
public @interface NetType {
    //有网络，包括Wifi/gprs
    public static final String AUTO = "CONNECT";
    //wifi
    public static final String WIFI = "WIFI";
    //PC/笔记本/PDA
    public static final String CMNET = "CMNET";
    //手机端
    public static final String CMWAP = "CMWAP";
    //没有网络
    public static final String NONE = "NONE";
}
