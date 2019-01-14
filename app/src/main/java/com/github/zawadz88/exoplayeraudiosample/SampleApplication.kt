package com.github.zawadz88.exoplayeraudiosample

import android.app.Application
import timber.log.Timber

class SampleApplication: Application() {

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
