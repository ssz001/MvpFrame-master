package com.ssz.studydemo.module.dagger

import android.os.Bundle
import com.ssz.studydemo.R
import com.ssz.studydemo.app.AppContext
import com.ssz.studydemo.base.dagger.DaggerMvpActivity
import com.ssz.studydemo.data.remote.SayBean
import com.ssz.studydemo.model.remote.net.Api
import com.ssz.studydemo.model.remote.net.retrofit
import com.ssz.studydemo.module.dagger.component.DaggerMvpExampleComponent
import com.ssz.studydemo.module.dagger.module.DaggerMvpModule
import kotlinx.android.synthetic.main.activity_custommvp.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DaggerMvpExampleActivity : DaggerMvpActivity<DaggerMvpExamplePresenter>(), IDaggerMvpContract.IView
        , CoroutineScope {

    @set:Inject
    var mApi: Api? = null
    private lateinit var job:Job


    override fun getLayoutId() = R.layout.activity_custommvp
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun initInject() {
        DaggerMvpExampleComponent.builder()
                .view(this)
                .addAppComponent(AppContext.getInstance().appComponent)
                .addDaggerMvpModule(DaggerMvpModule())
                .build()
                .inject(this)
    }

    override fun setEvent() {
        bt_get_joke.setOnClickListener {
            retrofit<List<SayBean>> {
                api = mApi?.getJoke(1, 2, "video")
                onSuccess {
                    showToast("success! ")
                }
            }
        }
    }

    override fun afterOnCreate(savedInstanceState: Bundle?) {
         job = Job()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}