package com.ssz.framejava.base.ui.dagger;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssz.framejava.base.BaseFragment;
import com.ssz.framejava.base.ui.dagger.func.DaggerPresenter;
import com.ssz.framejava.base.ui.view.IFragment;
import com.ssz.framejava.utils.ObjectHelper;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 9:06
 */
public abstract class DaggerMvpFragment<T extends DaggerPresenter> extends BaseFragment implements IFragment {

    protected CompositeDisposable mcDisposable;
    private BehaviorSubject<FragmentEvent> mLifecycleSubject;
    private Unbinder mBinder;
    @Inject
    protected T mPresenter;

    /**
     * 默认使用RxLifecycle
     */
    protected boolean useRxLifecycle(){
        return true;
    }

    /**
     * 生成Component
     */
    abstract void initInject();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeOnCreateView(savedInstanceState);
        View view = inflater.inflate(getLayoutId(), container, false);
        mBinder = ButterKnife.bind(this, view);
        initInject();
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        return view;
    }

    @Override
    public BehaviorSubject<FragmentEvent> provideBehaviorSubject(){
        if (ObjectHelper.nonNull(mLifecycleSubject))
            return mLifecycleSubject;
        else
            throw new IllegalStateException("please useRxLifecycle() return = true in"+this.getClass().getSimpleName());
    }

//    @Override
//    @NonNull
//    @CheckResult
//    public final Observable<FragmentEvent> lifecycle() {
//        if (ObjectHelper.isNull(mLifecycleSubject))
//            throw new IllegalStateException("please useRxLifecycle() return = true");
//        return mLifecycleSubject.hide();
//    }
//
//    @Override
//    @NonNull
//    @CheckResult
//    public final <R> LifecycleTransformer<R> bindUntilEvent(@NonNull FragmentEvent event) {
//        if (ObjectHelper.isNull(mLifecycleSubject))
//            throw new IllegalStateException("please useRxLifecycle() return = true");
//        return RxLifecycle.bindUntilEvent(mLifecycleSubject, event);
//    }
//
//    @Override
//    @NonNull
//    @CheckResult
//    public final <R> LifecycleTransformer<R> bindToLifecycle() {
//        if (ObjectHelper.isNull(mLifecycleSubject))
//            throw new IllegalStateException("please useRxLifecycle() return = true");
//        return RxLifecycleAndroid.bindFragment(mLifecycleSubject);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterOnCreateView(savedInstanceState);
    }

    @Override
    public void beforeOnCreateView(Bundle savedInstanceState) {
        // todo 本方法在super.onCreateView()前调用 ;
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useRxLifecycle()){
            mLifecycleSubject = BehaviorSubject.create();
            mLifecycleSubject.onNext(FragmentEvent.CREATE);
        }
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
}
