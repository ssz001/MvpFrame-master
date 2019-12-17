package com.ssz.framejava.module.custom;


import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.base.ui.mvp.func.BasePresenter;
import com.ssz.framejava.base.ui.mvp.func.BaseView;
import com.ssz.framejava.base.func.ISuccessListener;
import com.ssz.framejava.model.remote.net.execption.ApiException;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 9:56
 */
public interface CustomMvpContract {

    interface IView extends BaseView {
        void showProgress();
        void hideProgress();
        void success(List<SayBean> sayList);
        void error(ApiException ex);
    }

    interface IPresenter extends BasePresenter<IView> {
        Disposable getJoke();
        Disposable getJoke2(ISuccessListener<List<SayBean>> listener);
    }
}
