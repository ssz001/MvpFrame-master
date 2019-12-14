package com.ssz.studydemo.module.dagger


import com.ssz.studydemo.base.dagger.BasePresenter
import com.ssz.studydemo.base.dagger.di.scope.ActivityScope
import com.ssz.studydemo.data.remote.SayBean
import com.ssz.studydemo.model.remote.net.Api
import com.ssz.studydemo.model.remote.net.request
import javax.inject.Inject


@ActivityScope
class DaggerMvpExamplePresenter
@Inject constructor(val view : DaggerMvpExampleActivity): BasePresenter(),IDaggerMvpContract.IPresenter {

    @set:Inject
    var mApi: Api? = null

    override fun getJoke(page: Int, count: Int, type: String) {
        request<List<SayBean>> {
            api = mApi?.getJoke(1, 2, "video")
            onSuccess {
                view.showToast("success! ")
            }
            onFail{msg, _ ->
                view.showToast(msg)
            }
        }
    }

}