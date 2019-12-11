package com.ssz.base.func

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : zsp
 * time : 2019 10 2019/10/29 11:04
 * 仅仅加入了CompositeDisposable,如果没有用到@addDisposable,就不用继承这个类
 */
open class BaseRxPresenter{

    private val mcDisposable : CompositeDisposable by lazy { CompositeDisposable() }

    fun addDisposable(d: Disposable) {
        mcDisposable.add(d)
    }

    fun unSubscribe() {
        mcDisposable.dispose()
    }
}