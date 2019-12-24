package com.ssz.baselibrary.view.ui.mvp.func;

/**
 * @author : zsp
 * time : 2019 10 2019/10/10 10:54
 * IPresenter 层，对于Rxjava 操作的方法调用，
 * 返回Disposable 到 mView 层，mView 层 统一管理，也便于取消请求操作
 * supper 普通mvp
 */
public interface BasePresenter<T> {
   void attach(T view);
   void detach();
}
