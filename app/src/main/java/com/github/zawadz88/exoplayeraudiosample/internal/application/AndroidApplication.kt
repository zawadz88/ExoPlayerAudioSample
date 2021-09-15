package com.github.zawadz88.exoplayeraudiosample.internal.application

import android.app.Application
import com.github.zawadz88.exoplayeraudiosample.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    private fun initLogging() {
        Timber.uprootAll()
        // Write to logcat for builds that have logging enabled
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
