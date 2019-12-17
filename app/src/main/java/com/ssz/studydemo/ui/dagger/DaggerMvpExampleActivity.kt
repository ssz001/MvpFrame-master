package com.ssz.studydemo.ui.dagger

import android.os.Bundle
import com.ssz.studydemo.R
import com.ssz.studydemo.app.getAppContext
import com.ssz.studydemo.base.app.helper.AppHelper
import com.ssz.studydemo.base.ui.dagger.DaggerMvpActivity
import com.ssz.studydemo.ui.dagger.component.DaggerMvpExampleComponent
import com.ssz.studydemo.ui.dagger.module.DaggerMvpModule
import kotlinx.android.synthetic.main.activity_custommvp.*

class DaggerMvpExampleActivity : DaggerMvpActivity<DaggerMvpExamplePresenter>(), IDaggerMvpContract.IView {

    override fun getLayoutId() = R.layout.activity_custommvp

    override fun initInject() {
        DaggerMvpExampleComponent.builder()
                .view(this)
                .addAppComponent(AppHelper.getAppContext().mAppComponent)
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