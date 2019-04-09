package com.github.zawadz88.exoplayeraudiosample.presentation.main.di

import androidx.core.app.ComponentActivity
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.di.AudioPlayerActivityModule
import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Suppress("unused")
@Module(includes = [AudioPlayerActivityModule::class])
abstract class MainActivityModule {

    @Binds
    abstract fun bindComponentActivity(activity: MainActivity): ComponentActivity

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideAudioPlayerStateListener(activity: MainActivity): AudioPlayerStateListener = activity.audioPlayerStateListener
    }
}
