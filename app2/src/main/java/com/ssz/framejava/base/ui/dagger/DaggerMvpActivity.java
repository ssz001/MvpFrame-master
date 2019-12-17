package com.ssz.framejava.base.ui.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ssz.framejava.base.BaseActivity;
import com.ssz.framejava.base.ui.dagger.func.IActivity;
import com.ssz.framejava.base.ui.dagger.func.DaggerPresenter;
import com.ssz.framejava.utils.ObjectHelper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/19 9:01
 */
public abstract class DaggerMvpActivity<T extends DaggerPresenter> extends BaseActivity implements IActivity {

    @Nullable
    protected CompositeDisposable mcDisposable;

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCrete(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initInject();
        afterOnCreate(savedInstanceState);
    }

    @Override
    public void beforeOnCrete(Bundle savedInstanceState) {
//         todo 本方法在super.onCreate()前调用 ;
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
        if (ObjectHelper.nonNull(mPresenter)) mPresenter.detach();
        if (ObjectHelper.nonNull(mcDisposable)) mcDisposable.dispose();
        this.mPresenter = null;
        this.mcDisposable = null;
        super.onDestroy();
    }
}
