package com.ssz.login.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ssz.baselibrary.JumpUtil;
import com.ssz.baselibrary.view.BaseSplashActivity;

/**
 * @author : zsp
 * time : 2019 12 2019/12/23 15:15
 */
@Route(path = JumpUtil.splashActivity)
public class SplashActivity extends BaseSplashActivity {

    @Override
    protected void afterOnCreate(@Nullable Bundle savedInstanceState) {
//        startActivity(new Intent(this, UserActivity.class));
//        // 不需要转场动画
//        overridePendingTransition(0, 0);

        ARouter.getInstance()
                .build(JumpUtil.loginActivity)
                .withTransition(0,0)
                .navigation();

        finish();
    }
}
