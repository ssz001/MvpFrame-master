package com.ssz.studydemo.ui.dagger.di.module

import com.ssz.studydemo.base.ui.dagger.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author : zsp
 * time : 2019 12 2019/12/12 9:14
 */
@Module
class DaggerMvpModule {

    @ActivityScope
    @Provides
    fun providesString():String{
        return "xx"
    }

}