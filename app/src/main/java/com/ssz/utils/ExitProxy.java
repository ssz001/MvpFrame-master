package com.ssz.utils;

import android.app.Activity;
import android.content.Intent;

import com.ssz.base.Framework;
import com.ssz.utils.toast.ToastUtil;


/**
 * @author : zsp
 * time : 2019 11 2019/11/6 13:40
 * 主页面 按后退键两种情况封装
 * 1.finish()
 * 2.退出到后台
 *
 *     public boolean onKeyDown(int keyCode, KeyEvent event) {
 *         if (keyCode == KeyEvent.KEYCODE_BACK) {
 *             mExitProxy.exit();
 *             return true;
 *         }
 *         return super.onKeyDown(keyCode, event);
 *     }
 *
 */
public final class ExitProxy {
    private final Activity mActivity;
    // true 主页面，false finish()退出
    private final boolean isBackground;
    private long exitTime;

    public ExitProxy(Activity activity, boolean isBackground) {
        this.mActivity = activity;
        this.isBackground = isBackground;
    }

    public void exit(){
        if (isBackground){
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            mActivity.startActivity(home);
        }else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast(mActivity,"再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                Framework.exitApp();
            }
        }
    }
}
