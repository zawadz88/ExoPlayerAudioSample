package com.github.zawadz88.exoplayeraudiosample.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

class MainViewModel
@Inject
constructor() : ViewModel() {

    init {
        Timber.d("created: $this")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
    }

    fun loadContent() {
        Timber.d("Loading content")
    }

}
