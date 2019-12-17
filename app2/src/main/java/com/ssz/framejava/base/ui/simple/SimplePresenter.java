package com.ssz.framejava.base.ui.simple;

import android.os.Handler;

import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.model.remote.net.execption.ApiException;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/22 12:54
 */
public class SimplePresenter implements SimpleInteractor.IGetJokeFinishListener {

    private final CompositeDisposable mcDisposable = new CompositeDisposable();
    private ISimpleView mSimpleView;
    private SimpleInteractor mSimpleInteractor;

    public SimplePresenter(ISimpleView simpleView, SimpleInteractor interactor) {
        this.mSimpleView = simpleView;
        this.mSimpleInteractor = interactor;
    }

    private void addDisposable(Disposable d) {
        mcDisposable.add(d);
    }

    public void getJoke(int page, int count, String type) {
        // 更新View层
        if (null != mSimpleView) {
            mSimpleView.showProgress();
        }
        // 更新数据操作层
        new Handler().postDelayed(new Runnable() {// 模仿请求延迟
            @Override
            public void run() {
                Disposable d = mSimpleInteractor.getJoke(page, count, type, SimplePresenter.this);
                addDisposable(d);
            }
        }, 2000);
    }

//    public void getJoke2(int page, int count, String type) {
//        // 更新View层
//        if (null != mSimpleView) {
//            mSimpleView.showProgress();
//        }
//        // 更新数据操作层
//        // 模仿请求延迟
//        Disposable d = mSimpleInteractor.getJoke(page, count, type, new SimpleInteractor.IGetJokeFinishListener() {
//            @Override
//            public void getJokeFinish(List<SayBean> sayBeans) {
//                if (null != mSimpleView) {
//                    mSimpleView.hideProgress();
//                    mSimpleView.jokeShow(sayBeans);
//                }
//            }
//
//            @Override
//            public void getJokeFailure(ApiException ex) {
//                if (null != mSimpleView) {
//                    mSimpleView.hideProgress();
//                }
//            }
//        });
//    }

    @Override
    public void getJokeFinish(List<SayBean> sayBeans) {
        if (null != mSimpleView) {
            mSimpleView.hideProgress();
            mSimpleView.jokeShow(sayBeans);
        }
    }

    @Override
    public void getJokeFailure(ApiException ex) {
        if (null != mSimpleView) {
            mSimpleView.hideProgress();
        }
    }

    public void onDestroy() {
        mcDisposable.clear();
        this.mSimpleView = null;
    }
}
