package com.ssz.baselibrary.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ssz.baselibrary.BuildConfig;
import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.utils.ObjectHelper;
import com.ssz.baselibrary.utils.log.LogUtil;
import com.ssz.baselibrary.utils.toast.ToastUtil;


/**
 * @author : zsp
 * time : 2019 10 2019/10/10 15:25
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppHelper.get().isKilled()){
            LogUtil.d("AppStatus", "App后台被杀，重启!");
            if (BuildConfig.DEBUG){
                showToast("app" + "isKilled");
            }
            AppHelper.get().restartApp();
            return;
        }
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 手动重写
     */
    protected int getToolBarId() {
        return Integer.MAX_VALUE;
    }

    protected void setUpToolBar() {
        Toolbar toolbar = findViewById(getToolBarId());
        applyToolBar(toolbar);
    }

    protected void applyToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    protected void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected String getText(final TextView view) {
        if (ObjectHelper.nonNull(view)) {
            return view.getText().toString();
        }
        return "";
    }

    protected void dismiss(final Dialog dialog) {
        if (ObjectHelper.nonNull(dialog) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected void dismiss(final PopupWindow popupWindow) {
        if (ObjectHelper.nonNull(popupWindow) && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    protected void showToast(final String msg) {
        ToastUtil.showToast(this, msg);
    }

    protected void showToast(final String msg, int gravity) {
        ToastUtil.showToast(this, msg, gravity);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(new Intent(this, clz), requestCode);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param bundle      跳转所携带的信息
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
