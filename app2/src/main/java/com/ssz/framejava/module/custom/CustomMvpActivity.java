package com.ssz.framejava.module.custom;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ssz.framejava.R;
import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.base.mvp.MvpActivity;
import com.ssz.framejava.model.remote.net.execption.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 9:55
 */
public class CustomMvpActivity extends MvpActivity<CustomMvpContract.IPresenter> implements CustomMvpContract.IView {

    @BindView(R.id.bt_get_joke)
    Button btGetJoke;

    @Nullable
    @Override
    protected CustomMvpContract.IPresenter bindPresenter() {
        return new CustomMvpIPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custommvp;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

    }

    @OnClick({R.id.bt_get_joke})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_get_joke:
                 // 方法一
                 Disposable d =  mPresenter.getJoke();
                 addDisposable(d);

                 // 方法二
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 方法二
                        Disposable d2 =  mPresenter.getJoke2(result -> {
                            showToast("请求成功2");
                            Timber.d(result.toString());
                        });
                        addDisposable(d2);
                    }
                },1000);
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgress() {
       showToast("获取数据中...");
    }

    @Override
    public void hideProgress() {
        showToast("获取数据成功");
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
}
