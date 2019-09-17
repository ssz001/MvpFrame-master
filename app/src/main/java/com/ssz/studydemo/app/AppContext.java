package com.ssz.studydemo.app;

import com.ssz.frame.base.AbstractApp;


/**
 * @author : zsp
 * time : 2019 08 2019/8/23 10:03
 */
public class AppContext extends AbstractApp {

    public static AppContext getInstance(){
        return (AppContext) AppContext.instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

