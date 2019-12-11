package com.ssz.framejava.app;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : zsp
 * time : 2019 11 2019/11/6 16:36
 * 程序状态
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({AppStatus.KILLED,AppStatus.ALIVE})
public @interface AppStatus {
    String KILLED = "killed";
    String ALIVE = "alive";
}
