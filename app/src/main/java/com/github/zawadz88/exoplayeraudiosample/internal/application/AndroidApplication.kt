package com.github.zawadz88.exoplayeraudiosample.internal.application

import com.github.zawadz88.exoplayeraudiosample.BuildConfig
import com.github.zawadz88.exoplayeraudiosample.internal.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class AndroidApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }
    override fun applicationInjector(): AndroidInjector<out AndroidApplication> =
            DaggerApplicationComponent.builder().create(this)

    private fun initLogging() {
        Timber.uprootAll()
        // Write to logcat for builds that have logging enabled
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
