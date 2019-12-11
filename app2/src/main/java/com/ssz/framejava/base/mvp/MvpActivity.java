package com.ssz.framejava.base.mvp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ssz.framejava.base.BaseActivity;
import com.ssz.framejava.base.func.BasePresenter;
import com.ssz.framejava.utils.ObjectHelper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 10:55
 */
@SuppressLint("Registered")
public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity {

    protected T mPresenter;
    protected CompositeDisposable mcDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attach(bindPresenter());
        afterOnCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    protected void attach(@Nullable T presenter) {
        if (ObjectHelper.isNull(presenter)) return;
        this.mPresenter = presenter;
        this.mPresenter.attach(this);
    }

    @Nullable
    protected abstract T bindPresenter();

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
