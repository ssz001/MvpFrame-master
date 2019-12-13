package com.ssz.studydemo.module.dagger.module

import com.ssz.studydemo.base.dagger.di.scope.ActivityScope
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