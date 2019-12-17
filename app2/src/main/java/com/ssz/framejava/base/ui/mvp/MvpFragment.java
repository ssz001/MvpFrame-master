package com.ssz.framejava.base.ui.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssz.framejava.base.BaseFragment;
import com.ssz.framejava.base.ui.mvp.func.BasePresenter;
import com.ssz.framejava.base.ui.mvp.func.IFragment;
import com.ssz.framejava.utils.ObjectHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/4 8:51
 */
public abstract class MvpFragment<T extends BasePresenter> extends BaseFragment implements IFragment {

    protected T mPresenter;
    protected CompositeDisposable mcDisposable;
    private Unbinder mBinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeOnCreateView(savedInstanceState);
        View view = inflater.inflate(getLayoutId(), container, false);
        mBinder = ButterKnife.bind(this, view);
        attach(bindPresenter());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterOnCreateView(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    protected void attach(@Nullable T presenter) {
        if (ObjectHelper.isNull(presenter)) return;
        this.mPresenter = presenter;
        this.mPresenter.attach(this);
    }

    @Nullable
    protected abstract T bindPresenter();


    @Override
    public void beforeOnCreateView(Bundle savedInstanceState) {
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
        if (ObjectHelper.nonNull(mBinder)) mBinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        this.mPresenter = null;
        this.mBinder = null;
        this.mcDisposable = null;
        super.onDetach();
    }
}
