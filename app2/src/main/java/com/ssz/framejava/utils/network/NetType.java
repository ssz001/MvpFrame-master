package com.ssz.framejava.utils.network;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : zsp
 * time : 2019 10 2019/10/11 8:53
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({NetType.CONNECT,NetType.WIFI,NetType.MOBILE,NetType.NONE,
        NetType.MOBILE_2G,NetType.MOBILE_3G,NetType.MOBILE_4G,NetType.MOBILE_5G})
public @interface NetType {

    /**
     * 有网络，包括Wifi 和 网络
     */
    String CONNECT = "CONNECT";

    /**
     * wifi
     */
    String WIFI = "WIFI";

    /**
     * 数据流量网络
     */
    String MOBILE = "MOBILE";

    /**
     * 2G
     */
    String MOBILE_2G = "MOBILE_2G";

    /**
     * 3G
     */
    String MOBILE_3G = "MOBILE_3G";

    /**
     * 4G
     */
    String MOBILE_4G = "MOBILE_4G";

    /**
     * 5G,还没有
     */
    String MOBILE_5G = "MOBILE_5G";

    /**
     * 没有网络
     */
    String NONE = "NONE";
}
