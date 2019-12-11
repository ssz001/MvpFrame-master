package com.ssz.framejava.base.dagger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssz.framejava.base.BaseFragment;
import com.ssz.framejava.base.func.DaggerPresenter;
import com.ssz.framejava.utils.ObjectHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 9:06
 */
public abstract class DaggerMvpFragment<T extends DaggerPresenter> extends BaseFragment {

    protected CompositeDisposable mcDisposable;

    @Inject
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeOnCreateView(savedInstanceState);
        initInject();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterOnCreateView(savedInstanceState);
    }

    /**
     * 初始化Inject
     */
    protected abstract void initInject();

    protected abstract void afterOnCreateView(Bundle savedInstanceState);

    protected void beforeOnCreateView(Bundle savedInstanceState) {
        // todo 本方法在super.onCreateView()前调用 ;
    }

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
    public void onDestroyView() {
        if (ObjectHelper.nonNull(mPresenter)) mPresenter.detach();
        if (ObjectHelper.nonNull(mcDisposable)) mcDisposable.dispose();
        super.onDestroyView();
    }
}
