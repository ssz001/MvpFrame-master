package com.ssz.frame.mvp1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 08 2019/8/29 11:03
 */
public abstract class MVP1Activity<P extends IContract.IPresenter> extends AppCompatActivity {

    protected P mPresenter;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        attachView(bindPresenter());
        afterOnCreate(savedInstanceState);
    }

    private void attachView(P presenter){
        mPresenter = presenter;
//        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if (null != mCompositeDisposable){
            mCompositeDisposable.dispose();
        }
        mPresenter.detachView();
        super.onDestroy();
    }

    /**
     * 逻辑初始化区域
     */
    protected abstract void afterOnCreate(Bundle savedInstanceState);

    /**
     * 在 onCreate() 之前调用
     */
    protected void beforeOnCreate(Bundle savedInstanceState){}

    /**
     * View
     */
    protected abstract int getLayoutId();

    /**
     * 绑定Presenter
     */
    protected abstract P bindPresenter();

    /**
     * 加入取消订阅容器
     */
    protected void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }
}
