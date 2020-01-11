package com.ssz.framejava.base.ui.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssz.framejava.base.BaseFragment;
import com.ssz.framejava.base.ui.mvp.func.BasePresenter;
import com.ssz.framejava.base.ui.view.IFragment;
import com.ssz.framejava.utils.ObjectHelper;
import com.trello.rxlifecycle2.android.FragmentEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author : zsp
 * time : 2019 11 2019/11/4 8:51
 */
public abstract class MvpFragment<T extends BasePresenter> extends BaseFragment implements IFragment {

    protected T mPresenter;
    protected CompositeDisposable mcDisposable;
    private Unbinder mBinder;
    private BehaviorSubject<FragmentEvent> mLifecycleSubject;

    /**
     * 默认使用RxLifecycle
     */
    protected boolean useRxLifecycle(){
        return true;
    }

    @Override
    public BehaviorSubject<FragmentEvent> provideBehaviorSubject(){
        if (ObjectHelper.nonNull(mLifecycleSubject))
            return mLifecycleSubject;
        else
            throw new IllegalStateException("please useRxLifecycle() return = true in"+this.getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeOnCreateView(savedInstanceState);
        View view = inflater.inflate(getLayoutId(), container, false);
        mBinder = ButterKnife.bind(this, view);
        attach(bindPresenter());
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (useRxLifecycle()){
            mLifecycleSubject = BehaviorSubject.create();
        }
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        if (ObjectHelper.nonNull(mPresenter)) mPresenter.detach();
        if (ObjectHelper.nonNull(mcDisposable)) mcDisposable.dispose();
        if (ObjectHelper.nonNull(mBinder))mBinder.unbind();
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onDetach() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.DETACH);
        this.mBinder = null;
        this.mPresenter = null;
        super.onDetach();
    }

    @Override
    public void addDisposable(@Nullable Disposable d) {
        if (ObjectHelper.isNull(d)) return;
        if (d.isDisposed())return;
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
}
