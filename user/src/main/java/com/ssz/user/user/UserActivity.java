package com.ssz.user.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.ssz.baselibrary.JumpUtil;
import com.ssz.baselibrary.app.helper.AppHelper;
import com.ssz.baselibrary.view.ui.dagger.DaggerMvpActivity;
import com.ssz.user.R;
import com.ssz.user.user.di.DaggerUserComponent;
import com.ssz.user.user.di.UserModule;

/**
 * @author : zsp
 * time : 2019 12 2019/12/24 9:25
 */
@Route(path = JumpUtil.userActivity)
public class UserActivity extends DaggerMvpActivity<UserPresenter> implements IUserContract.IView {

    @Autowired(name = "from")
    String from;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public void initInject() {
        DaggerUserComponent.builder()
                .view(this)
                .appComponent(AppHelper.getAppContext().getAppComponent())
                .userModule(new UserModule())
                .build()
                .inject(this);
    }

    @Override
    public void afterOnCreate(@Nullable Bundle savedInstanceState) {
           showToast(from+"_user模块_UserActivity");
    }
}
