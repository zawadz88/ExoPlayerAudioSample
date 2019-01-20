package com.github.zawadz88.exoplayeraudiosample.internal.di.component

import com.github.zawadz88.exoplayeraudiosample.internal.application.AndroidApplication
import com.github.zawadz88.exoplayeraudiosample.internal.di.module.ApplicationModule
import com.github.zawadz88.exoplayeraudiosample.presentation.main.di.MainFeatureContributorModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        MainFeatureContributorModule::class]
)
interface ApplicationComponent : AndroidInjector<AndroidApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AndroidApplication>() {

        abstract override fun build(): ApplicationComponent
    }
}
