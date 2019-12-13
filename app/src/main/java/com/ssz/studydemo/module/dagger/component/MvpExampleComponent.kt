package com.ssz.studydemo.module.dagger.component

import com.ssz.studydemo.base.dagger.di.component.AppComponent
import com.ssz.studydemo.base.dagger.di.scope.ActivityScope
import com.ssz.studydemo.module.dagger.DaggerMvpExampleActivity
import com.ssz.studydemo.module.dagger.module.DaggerMvpModule
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