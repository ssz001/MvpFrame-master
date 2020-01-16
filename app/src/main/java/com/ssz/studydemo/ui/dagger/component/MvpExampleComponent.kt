package com.ssz.studydemo.ui.dagger.component

import com.ssz.studydemo.base.ui.dagger.di.component.AppComponent
import com.ssz.studydemo.base.ui.dagger.di.scope.ActivityScope
import com.ssz.studydemo.ui.dagger.DaggerMvpExampleActivity
import com.ssz.studydemo.ui.dagger.module.DaggerMvpModule
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class],modules = [DaggerMvpModule::class])
interface MvpExampleComponent {
    fun inject(activity : DaggerMvpExampleActivity)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun view(view : DaggerMvpExampleActivity) : Builder
        fun addAppComponent(appComponent: AppComponent):Builder
        fun addDaggerMvpModule(module: DaggerMvpModule):Builder
        fun build():MvpExampleComponent
    }
}