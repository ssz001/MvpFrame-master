package com.ssz.framejava.module.dagger;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ssz.framejava.R;
import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.app.AppContext;
import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.base.ui.dagger.DaggerMvpActivity;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.module.dagger.di.component.DaggerMvpExampleComponent;
import com.ssz.framejava.module.dagger.di.module.DaggerMvpModule;
import com.ssz.framejava.utils.log.LogUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 10:41
 */
public class DaggerMvpExampleActivity extends DaggerMvpActivity<DaggerMvpExamplePresenter> implements IDaggerMvpContract.IView {

    @Inject
    protected AppContext appContext;

    @Inject String stringTest;

    @Override
    public void initInject() {
        DaggerMvpExampleComponent.builder()
                .view(this)
                .addAppComponent(AppHelper.getAppContext().getAppComponent())
                .daggerMvpModule(new DaggerMvpModule())
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custommvp;
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        // false
        Timber.d("appContext == null :%s", (appContext == null));
        Timber.d("stringTest :%s", stringTest);
        LogUtil.d("tytytyi","timer ");
    }

    @OnClick({R.id.bt_get_joke})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_get_joke:
                // 方法一
                Disposable d =  mPresenter.getJoke();
                addDisposable(d);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 方法二
                        Disposable d2 =  mPresenter.getJoke2(result -> {
                            showToast("请求成功2 - mApi");
                            Timber.d(result.toString());
                        });
                        addDisposable(d2);
                    }
                },2000);
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void success(List<SayBean> sayList) {
        showToast("请求成功1");
        Timber.d(sayList.toString());
    }

    @Override
    public void error(ApiException ex) {
        showToast(ex.getMessage());
    }

    @Override
    protected void onDestroy() {
        this.appContext = null;
        super.onDestroy();
    }
}
