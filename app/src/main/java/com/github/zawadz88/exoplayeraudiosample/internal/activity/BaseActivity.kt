package com.github.zawadz88.exoplayeraudiosample.internal.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate: $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: $this")
    }
}
