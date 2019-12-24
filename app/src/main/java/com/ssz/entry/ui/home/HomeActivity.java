package com.ssz.entry.ui.home;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ssz.baselibrary.JumpUtil;
import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.app.helper.ExitAppBroadcast;
import com.ssz.baselibrary.utils.ExitProxy;
import com.ssz.baselibrary.view.ui.dagger.DaggerMvpActivity;
import com.ssz.entry.R;
import com.ssz.entry.ui.home.di.component.DaggerHomeComponent;
import com.ssz.entry.ui.home.di.module.HomeModule;

import javax.inject.Inject;

import butterknife.OnClick;
import dagger.Lazy;

/**
 * @author : zsp
 * time : 2019 12 2019/12/23 15:20
 */
@Route(path = JumpUtil.homeActivity)
public class HomeActivity extends DaggerMvpActivity<HomePresenter> implements IHomeContract.IView {

    @Autowired(name = "key1")
    String from;
    @Inject
    Lazy<Handler> mMainHandler;
    @Inject
    Lazy<String> moduleName;
    @Inject
    Lazy<ExitProxy> mExitProxy;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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

    @OnClick({R.id.bt_login,R.id.bt_user})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt_login:
                ARouter.getInstance().build(JumpUtil.loginActivity)
                        .withString("from","HomeActivity")
                        .navigation();
                break;
            case R.id.bt_user:
                ARouter.getInstance().build(JumpUtil.userActivity)
                        .withString("from","HomeActivity")
                        .navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            mExitProxy.get().exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void afterOnCreate(@Nullable Bundle savedInstanceState) {
        // 注册退出广播
        registerReceiver(new ExitAppBroadcast(),new IntentFilter(ExitAppBroadcast.EDIT_ACTION));
    }
}
