package com.ssz.login.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.ssz.baselibrary.JumpUtil;
import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.view.ui.dagger.DaggerMvpActivity;
import com.ssz.login.R;
import com.ssz.login.login.di.component.DaggerLoginComponent;
import com.ssz.login.login.di.module.LoginModule;

@Route(path = JumpUtil.loginActivity)
public class LoginActivity extends DaggerMvpActivity<LoginPresenter> implements ILoginContract.IView {

    @Autowired(name = "from")
    String from;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initInject() {
        DaggerLoginComponent.builder()
                .view(this)
                .appComponent(AppHelper.getAppContext().getAppComponent())
                .loginModule(new LoginModule())
                .build()
                .inject(this);
    }

    @Override
    public void afterOnCreate(@Nullable Bundle savedInstanceState) {
             showToast(from+"_app模块_HomeActivity");
    }
}
