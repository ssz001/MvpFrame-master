package com.ssz.framejava.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ssz.framejava.R;
import com.ssz.framejava.T.Test;
import com.ssz.framejava.base.BaseSplashActivity;
import com.ssz.framejava.module.home.HomeActivity;


/**
 * @author : zsp
 * time : 2019 11 2019/11/5 15:33
 */
public class SplashActivity extends BaseSplashActivity {

    @Override
    protected void afterOnCreate(@Nullable Bundle savedInstanceState) {

        startActivity(new Intent(this, HomeActivity.class));
        // 不需要转场动画
        overridePendingTransition(0, 0);

        // 触发测试（T）
        new Test().startTest();

        finish();
    }

    @Override
    protected long getDelayTime() {
        return super.getDelayTime();
    }

    @Override
    protected void resetTheme() {
        setTheme(R.style.AppTheme_NoTitle);
    }
}
