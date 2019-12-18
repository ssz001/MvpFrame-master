package com.ssz.framejava.base.ui.mvp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ssz.framejava.base.BaseActivity;
import com.ssz.framejava.base.ui.mvp.func.BasePresenter;
import com.ssz.framejava.base.ui.view.IActivity;
import com.ssz.framejava.utils.ObjectHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 10:55
 */
@SuppressLint("Registered")
public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity implements IActivity {

    protected T mPresenter;
    protected CompositeDisposable mcDisposable;

    private BehaviorSubject<ActivityEvent> mLifecycleSubject;

    /**
     * 默认使用RxLifecycle
     */
    protected boolean useRxLifecycle(){
        return true;
    }

    @Nullable
    protected abstract T bindPresenter();

    @Override
    public BehaviorSubject<ActivityEvent> provideBehaviorSubject(){
        if (ObjectHelper.nonNull(mLifecycleSubject))
            return mLifecycleSubject;
        else
            throw new IllegalStateException("please useRxLifecycle() return = true in"+this.getClass().getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCrete(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (useRxLifecycle()){
            mLifecycleSubject = BehaviorSubject.create();
            mLifecycleSubject.onNext(ActivityEvent.CREATE);
        }
        ButterKnife.bind(this);
        attach(bindPresenter());
        afterOnCreate(savedInstanceState);
    }

//    @Nonnull
//    @Override
//    public Observable<ActivityEvent> lifecycle() {
//        if (ObjectHelper.isNull(mLifecycleSubject))
//            throw new IllegalStateException("please useRxLifecycle() return = true");
//       return mLifecycleSubject.hide();
//    }
//
//    @Nonnull
//    @Override
//    public <R> LifecycleTransformer<R> bindUntilEvent(@Nonnull ActivityEvent event) {
//        if (ObjectHelper.isNull(mLifecycleSubject))
//            throw new IllegalStateException("please useRxLifecycle() return = true");
//        return RxLifecycle.bindUntilEvent(mLifecycleSubject, event);
//    }
//
//    @Nonnull
//    @Override
//    public <R> LifecycleTransformer<R> bindToLifecycle() {
//        if (ObjectHelper.isNull(mLifecycleSubject))
//            throw new IllegalStateException("please useRxLifecycle() return = true");
//        return RxLifecycleAndroid.bindActivity(mLifecycleSubject);
//    }

    @Override
    public void beforeOnCrete(Bundle savedInstanceState) {
        // todo 本方法在super.onCreate()前调用 ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }


    @SuppressWarnings("unchecked")
    protected void attach(@Nullable T presenter) {
        if (ObjectHelper.isNull(presenter)) return;
        this.mPresenter = presenter;
        this.mPresenter.attach(this);
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
    protected void onDestroy() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(ActivityEvent.DESTROY);
        if (ObjectHelper.nonNull(mPresenter)) mPresenter.detach();
        if (ObjectHelper.nonNull(mcDisposable)) mcDisposable.dispose();
        super.onDestroy();
    }
}