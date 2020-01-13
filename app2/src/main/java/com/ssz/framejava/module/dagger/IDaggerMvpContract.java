package com.ssz.framejava.module.dagger;

import com.ssz.framejava.T.SayBean;
import com.ssz.framejava.base.ui.mvp.func.BaseView;
import com.ssz.framejava.base.ui.dagger.func.DaggerPresenter;
import com.ssz.framejava.base.func.ISuccessListener;
import com.ssz.framejava.model.remote.net.execption.ApiException;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 11 2019/11/20 10:42
 */
public interface IDaggerMvpContract {

    interface IView extends BaseView {
        void showProgress();
        void hideProgress();
        void success(List<SayBean> sayList);
        void error(ApiException ex);
    }

    interface IPresenter extends DaggerPresenter {
        Disposable getJoke();
        Disposable getJoke2(ISuccessListener<List<SayBean>> listener);
    }
}
