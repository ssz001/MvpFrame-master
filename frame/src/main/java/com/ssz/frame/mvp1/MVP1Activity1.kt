package com.ssz.frame.mvp1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author : zsp
 * time : 2019 09 2019/9/17 16:04
 */
abstract class MVP1Activity1<P : IContract.IPresenter<IContract.IView>> : AppCompatActivity() {

//   lateinit 只是说明延迟初始化，可以确定的是它一定会被初始化，所以用的时候发现可以不用?.
//   lateinit var mCompositeDisposable2: CompositeDisposable

     var mPresenter: P? = null
     var mCompositeDisposable: CompositeDisposable? = null

//  get不行 mCompositeDisposable?.dispose() 会初始化并且dispose(),所以是多余操作 lazy()｛｝也是一样的
//        get() {
//            if (field == null) {
//                field = CompositeDisposable()
//            }
//            return field
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        ButterKnife.bind(this)
        attachView(bindPresenter())
        afterOnCreate(savedInstanceState)
    }

    abstract fun afterOnCreate(savedInstanceState : Bundle?)
    abstract fun getLayoutId() : Int
    abstract fun bindPresenter():P
    open fun beforeOnCreate(savedInstanceState : Bundle?){

    }

    open fun attachView(presenter: P) {
        mPresenter = presenter
//        mPresenter.attachView(this)
    }


    fun addDisposable(disposable: Disposable) {
//  判断是否已经初始化
//  if (!this::mCompositeDisposable.isInitialized){
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    override fun onDestroy() {
        mCompositeDisposable?.dispose()
        super.onDestroy()
    }
}