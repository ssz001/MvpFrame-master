package com.ssz.frame.mvp1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 08 2019/8/29 11:08
 */
public abstract class MVP1Fragment<P extends IContract.IPresenter> extends Fragment {

    protected P mPresenter;
    private CompositeDisposable mCompositeDisposable;
    private Unbinder mBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(),container,false);
        mBinder = ButterKnife.bind(this,rootView);
        mPresenter = bindPresenter();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (null != mCompositeDisposable)mCompositeDisposable.dispose();
        mBinder.unbind();
        super.onDestroyView();
    }

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
