package com.ssz.framejava.module.home;


import com.ssz.framejava.base.func.BasePresenter;
import com.ssz.framejava.base.func.BaseView;
import com.ssz.framejava.func.ISuccessListener;

import io.reactivex.disposables.Disposable;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 11:08
 */
public interface IHomeContract {

    interface IView extends BaseView {
        void success();
        void error();
    }

    interface IPresenter extends BasePresenter<IView> {
        Disposable login();
        Disposable login2(ISuccessListener<Boolean> listener);
    }
}
