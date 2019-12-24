package com.ssz.baselibrary.view.ui.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ssz.baselibrary.view.BaseActivity;
import com.ssz.baselibrary.view.ui.dagger.func.DaggerPresenter;
import com.ssz.baselibrary.view.ui.IActivity;
import com.ssz.baselibrary.utils.ObjectHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 9:01
 */
public abstract class DaggerMvpActivity<T extends DaggerPresenter> extends BaseActivity
        implements IActivity {
    @Nullable
    protected CompositeDisposable mcDisposable;
    private BehaviorSubject<ActivityEvent> mLifecycleSubject;
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
    public abstract void initInject();

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
        if (useRxLifecycle()){
            mLifecycleSubject = BehaviorSubject.create();
            mLifecycleSubject.onNext(ActivityEvent.CREATE);
        }
        int layoutId = getLayoutId();
        if (layoutId != 0){
            setContentView(layoutId);
            ButterKnife.bind(this);
        }
        initInject();
        afterOnCreate(savedInstanceState);
    }

    @Override
    public void beforeOnCrete(Bundle savedInstanceState) {
//         todo 本方法在super.onCreate()前调用 ;
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

    @Override
    protected void onDestroy() {
        if (ObjectHelper.nonNull(mLifecycleSubject))
            mLifecycleSubject.onNext(ActivityEvent.DESTROY);
        if (ObjectHelper.nonNull(mPresenter)) mPresenter.detach();
        if (ObjectHelper.nonNull(mcDisposable)) mcDisposable.dispose();
        this.mPresenter = null;
        this.mcDisposable = null;
        super.onDestroy();
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
