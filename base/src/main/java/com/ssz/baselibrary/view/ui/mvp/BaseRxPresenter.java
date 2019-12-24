package com.ssz.baselibrary.view.ui.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.ssz.baselibrary.view.ui.mvp.func.BasePresenter;
import com.ssz.baselibrary.utils.ObjectHelper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/11 13:08
 */
public class BaseRxPresenter<T> implements BasePresenter<T> {

    protected T mView;
    private CompositeDisposable mcDisposable;

    @Override
    public void attach(@Nullable T view) {
        this.mView = view;
    }

    protected void addDisposable(@NonNull Disposable disposable) {
        if (ObjectHelper.isNull(mcDisposable)) {
            mcDisposable = new CompositeDisposable();
        }
        mcDisposable.add(disposable);
    }

    protected void dispose() {
        if (ObjectHelper.nonNull(mcDisposable)) {
            mcDisposable.dispose();
        }
    }

    @Override
    public void detach() {
        dispose();
        this.mcDisposable = null;
        this.mView = null;
    }
}
