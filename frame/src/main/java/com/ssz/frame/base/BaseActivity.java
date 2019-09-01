package com.ssz.frame.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.annotation.Nullable;


/**
 * @author : zsp
 * time : 2019 08 2019/8/23 9:36
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        setEvent();
    }
    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setEvent();

    /**
     * 以下改动目的：字体大小不随系统字体大小的改变而改变
     * @param newConfig
     */

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1) { //fontScale不为1，需要强制设置为1
            getResources();
        }
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1) { //fontScale不为1，需要强制设置为1
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }


}
