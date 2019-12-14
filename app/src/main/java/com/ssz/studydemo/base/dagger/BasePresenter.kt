package com.ssz.studydemo.base.dagger

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter : CoroutineScope {

    private var mcDisposable : CompositeDisposable? = null
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun addDisposable(d: Disposable){
        if (null == mcDisposable){
            mcDisposable = CompositeDisposable()
        }
        mcDisposable!!.add(d)
    }

    open fun detach(){
        job.cancel()
        mcDisposable?.dispose()
    }

}