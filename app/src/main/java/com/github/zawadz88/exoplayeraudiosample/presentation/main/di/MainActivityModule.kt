package com.github.zawadz88.exoplayeraudiosample.presentation.main.di

import androidx.fragment.app.FragmentActivity
import com.github.zawadz88.audioservice.AudioPlayerStateListener
import com.github.zawadz88.audioservice.di.AudioPlayerActivityModule
import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Suppress("unused")
@InstallIn(ActivityComponent::class)
@Module(includes = [AudioPlayerActivityModule::class])
object MainActivityModule {

    @Provides
    fun FragmentActivity.provideMainActivity(): MainActivity = this as MainActivity

    @Provides
    fun provideAudioPlayerStateListener(activity: MainActivity): AudioPlayerStateListener = activity.audioPlayerStateListener
}
