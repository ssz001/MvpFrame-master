package com.ssz.studydemo.base.dagger.di.module

import com.ssz.studydemo.app.AppContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author : zsp
 * time : 2019 12 2019/12/11 14:28
 */
@Module
class AppModule(val context: AppContext) {

    @Singleton
    @Provides
    fun provideAppContext(): AppContext {
        return context
    }
}