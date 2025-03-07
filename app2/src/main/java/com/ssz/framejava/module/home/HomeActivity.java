package com.ssz.framejava.module.home;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;

import com.ssz.framejava.R;
import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.base.app.helper.ExitAppBroadcast;
import com.ssz.framejava.base.ui.mvp.MvpActivity;
import com.ssz.framejava.base.ui.simple.SimpleActivity;
import com.ssz.framejava.module.custom.CustomMvpActivity;
import com.ssz.framejava.module.dagger.DaggerMvpExampleActivity;
import com.ssz.framejava.utils.ExitProxy;

import butterknife.OnClick;


/**
 * @author : zsp
 * time : 2019 10 2019/10/10 13:17
 */
public class HomeActivity extends MvpActivity<IHomeContract.IPresenter> implements IHomeContract.IView {

    @NonNull
    @Override
    protected IHomeContract.IPresenter bindPresenter() {
        return new HomePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        registerReceiver(new ExitAppBroadcast(),new IntentFilter(ExitAppBroadcast.EDIT_ACTION));
    }

    @OnClick({R.id.bt_content,R.id.bt_dagger,R.id.bt_custom,R.id.bt_simple})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_content:
                AppHelper.exitApp();
//                  finish();
//                Framework.exitApp();
//                Framework.restart();
//                ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
//                manager.killBackgroundProcesses(getPackageName());
                break;
            case R.id.bt_dagger:
                startActivity(DaggerMvpExampleActivity.class);
                break;
            case R.id.bt_custom:
                startActivity(CustomMvpActivity.class);
                break;
            case R.id.bt_simple:
                startActivity(SimpleActivity.class);
                break;
            default:
                break;
        }
    }

    private final ExitProxy mExitProxy = new ExitProxy(this, false);
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mExitProxy.exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void success() {

    }

    @Override
    public void error() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
