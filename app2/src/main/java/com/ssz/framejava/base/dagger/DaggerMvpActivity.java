package com.ssz.framejava.base.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ssz.framejava.base.BaseActivity;
import com.ssz.framejava.base.func.DaggerPresenter;
import com.ssz.framejava.utils.ObjectHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 9:01
 */
public abstract class DaggerMvpActivity<T extends DaggerPresenter> extends BaseActivity {

    @Nullable
    protected CompositeDisposable mcDisposable;

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();
        afterOnCreate(savedInstanceState);
    }

    /**
     * Inject
     */
    protected abstract void initInject();

    /**
     * 初始化逻辑代码
     */
    protected abstract void afterOnCreate(Bundle savedInstanceState);

    protected void addDisposable(@Nullable Disposable d) {
        if (ObjectHelper.isNull(d)) return;
        if (ObjectHelper.isNull(mcDisposable)) {
            mcDisposable = new CompositeDisposable();
        }
        mcDisposable.add(d);
    }

    protected void removeDisposable(@Nullable Disposable d) {
        if (ObjectHelper.isNull(d)) return;
        if (ObjectHelper.nonNull(mcDisposable)) {
            boolean remove = mcDisposable.remove(d);
            if (!remove) d.dispose();
        } else {
            d.dispose();
        }
    }

    @Override
    protected void onDestroy() {
        if (ObjectHelper.nonNull(mPresenter)) mPresenter.detach();
        if (ObjectHelper.nonNull(mcDisposable)) mcDisposable.dispose();
        super.onDestroy();
    }
}
