package com.ssz.framejava.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

@SuppressLint("Registered")
public abstract class CustomActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId != 0){
            setContentView(layoutId);
            ButterKnife.bind(this);
        }
        initView();
        initData();
    }

    protected void beforeOnCreate(){

    }
    abstract int getLayoutId();
    abstract void initView();
    abstract void initData();

}
