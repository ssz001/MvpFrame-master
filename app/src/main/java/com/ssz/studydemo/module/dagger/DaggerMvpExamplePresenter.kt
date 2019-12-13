package com.ssz.studydemo.module.dagger


import com.ssz.studydemo.base.dagger.di.scope.ActivityScope
import javax.inject.Inject


@ActivityScope
class DaggerMvpExamplePresenter @Inject constructor(val view : DaggerMvpExampleActivity): IDaggerMvpContract.IPresenter {
}