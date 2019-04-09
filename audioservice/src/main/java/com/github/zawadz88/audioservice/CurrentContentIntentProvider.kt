package com.github.zawadz88.audioservice

import android.content.Context
import android.content.Intent

interface CurrentContentIntentProvider {

    fun provideCurrentContentIntent(context: Context): Intent
}
