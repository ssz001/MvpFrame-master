package com.ssz.studydemo.ui.dagger.module

import com.ssz.studydemo.base.ui.dagger.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class DaggerMvpModule {

    @Provides
    @ActivityScope
    fun getName() : String{
        return "ss"
    }
}