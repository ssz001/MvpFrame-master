package com.ssz.framejava.module.dagger;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.ssz.framejava.R;
import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.app.AppContext;
import com.ssz.framejava.base.app.helper.AppHelper;
import com.ssz.framejava.base.ui.dagger.DaggerMvpActivity;
import com.ssz.framejava.model.remote.net.execption.ApiException;
import com.ssz.framejava.module.dagger.di.component.DaggerMvpExampleComponent;
import com.ssz.framejava.module.dagger.di.module.DaggerMvpModule;
import com.ssz.framejava.module.home.dialog.DialogFragmentEx;

import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 10:41
 */
public class DaggerMvpExampleActivity extends DaggerMvpActivity<DaggerMvpExamplePresenter> implements IDaggerMvpContract.IView {

    @Inject Handler mHandler;
    @Inject AppContext appContext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custommvp;
    }

    @Override
    public void initInject() {
        // view() 在本方法初始化完成后，都可以用@Inject 获取对象,或者在类的（@Inject）构造方法中如果有view参数，
        // 会直接取，详细见presenter
        DaggerMvpExampleComponent.builder()
                .view(this)
                .addAppComponent(AppHelper.getAppContext().getAppComponent())
                .daggerMvpModule(new DaggerMvpModule())
                .build()
                .inject(this);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        // false
        Timber.d("appContext == null :%s", (appContext == null));
    }

    DialogFragmentEx dialogEx;

    @OnClick({R.id.bt_get_joke})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_get_joke:
                Log.d("op","null");
//                if (null == dialogEx){
//                    dialogEx = new DialogFragmentEx();
//                }
//                dialogEx.show(getSupportFragmentManager(),"p");

////                test1();
                // 方法一
                Disposable d =  mPresenter.getJoke();
                addDisposable(d);

//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 方法二
//                        Disposable d2 =  mPresenter.getJoke2(result -> {
//                            showToast("请求成功2 - mApi");
//                            Timber.d(result.toString());
//                        });
//                        addDisposable(d2);
//                    }
//                },2000);
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgress() {
        runOnUiThread(() -> {
            if (null == dialogEx){
                dialogEx = new DialogFragmentEx(this);
            }
            dialogEx.show();
        });
    }

    @Override
    public void hideProgress() {
//        if (null != dialogEx){
//            dialogEx.dismiss();
//        }
    }

    @Override
    public void success(List<SayBean> sayList) {
        showToast("请求成功1");
        Timber.d(sayList.toString());
    }

    @Override
    public void error(ApiException ex) {
        showToast(ex.getMessage());
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        this.appContext = null;
    }

//    private void test1() {
//        // 第一个Observable（即我们自己的Observable）
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                while (true) {
//                    emitter.onNext("aa");
//                }
//            }
//        }).subscribeOn(Schedulers.io())
////                .compose(bindUntilEvent(ActivityEvent.PAUSE))
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d("d_test", "onSubscribe: ");
//                        addDisposable(d);
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.d("d_test", "onSubscribe: " + s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("d_test", "onSubscribe: " + "onComplete()");
//                    }
//                });
//    }
}
