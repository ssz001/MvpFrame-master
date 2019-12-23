package com.ssz.framejava.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.ssz.baselibrary.JumpUtil;
import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.view.ui.dagger.DaggerMvpActivity;
import com.ssz.framejava.R;
import com.ssz.framejava.ui.home.di.component.DaggerHomeComponent;
import com.ssz.framejava.ui.home.di.module.HomeModule;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * @author : zsp
 * time : 2019 12 2019/12/23 15:20
 */
@Route(path = JumpUtil.HomeActivity)
public class HomeActivity extends DaggerMvpActivity<HomePresenter> implements IHomeContract.IView {

    @Autowired(name = "key1")
    String from;
    @Inject
    Lazy<Handler> mMainHandler;
    @Inject
    Lazy<String> moduleName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_begin;
    }

    @Override
    public void initInject() {
        DaggerHomeComponent.builder()
                .view(this)
                .appComponent(AppHelper.getAppContext().getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);
    }

    @Override
    public void afterOnCreate(@Nullable Bundle savedInstanceState) {
         showToast(from);
    }
}
