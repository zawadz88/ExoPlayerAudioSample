package com.github.zawadz88.exoplayeraudiosample.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import com.github.zawadz88.exoplayeraudiosample.domain.interactor.LoadPlaylist
import com.github.zawadz88.exoplayeraudiosample.internal.rx.EmptySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadPlaylist: LoadPlaylist
) : ViewModel() {

    init {
        Timber.d("created: $this")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
        loadPlaylist.dispose()
    }

    fun loadContent() {
        Timber.d("Loading content")
        loadPlaylist(observer = object : EmptySingleObserver<List<String>>() {
            override fun onSuccess(result: List<String>) {
                Timber.d("songs: $result")
            }
        })
    }
}
