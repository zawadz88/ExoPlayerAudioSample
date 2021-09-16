package com.github.zawadz88.audioservice.di

import com.github.zawadz88.audioservice.AudioPlayerStateListener
import dagger.BindsInstance
import dagger.hilt.DefineComponent
import dagger.hilt.android.components.ActivityComponent

@DefineComponent(parent = ActivityComponent::class)
interface AudioPlayerActivityComponent {

    @DefineComponent.Builder
    interface Builder {

        fun stateListener(@BindsInstance stateListener: AudioPlayerStateListener): Builder

        fun build(): AudioPlayerActivityComponent
    }
}
