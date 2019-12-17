package com.ssz.studydemo.ui.dagger.di.component

import com.ssz.studydemo.base.ui.dagger.di.component.AppComponent
import com.ssz.studydemo.base.ui.dagger.di.scope.ActivityScope
import com.ssz.studydemo.ui.dagger.DaggerMvpExampleActivity
import com.ssz.studydemo.ui.dagger.di.module.DaggerMvpModule
import dagger.BindsInstance
import dagger.Component

/**
 * @author : zsp
 * time : 2019 12 2019/12/12 9:13
 */
@ActivityScope
@Component(dependencies = [AppComponent::class],modules = [DaggerMvpModule::class])
interface MvpExampleComponent {
    fun inject(exampleActivity : DaggerMvpExampleActivity)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun view(view : DaggerMvpExampleActivity):Builder
        fun addAppComponent(appComponent : AppComponent):Builder
        fun daggerMvpModule(module : DaggerMvpModule):Builder
        fun build() : MvpExampleComponent
    }
}