package com.ssz.studydemo.module.dagger

import android.os.Bundle
import com.ssz.studydemo.R
import com.ssz.studydemo.app.AppContext
import com.ssz.studydemo.base.dagger.DaggerMvpActivity
import com.ssz.studydemo.module.dagger.component.DaggerMvpExampleComponent
import com.ssz.studydemo.module.dagger.module.DaggerMvpModule
import kotlinx.android.synthetic.main.activity_custommvp.*

class DaggerMvpExampleActivity : DaggerMvpActivity<BaseMvpExamplePresenter>(), IDaggerMvpContract.IView {

    override fun getLayoutId() = R.layout.activity_custommvp

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
            mPresenter?.getJoke(1, 2, "video")
        }
    }

    override fun afterOnCreate(savedInstanceState: Bundle?) {

    }

    override fun onDestroy() {
        mPresenter?.detach()
        super.onDestroy()
    }
}