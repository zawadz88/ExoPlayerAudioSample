package com.github.zawadz88.exoplayeraudiosample.presentation.main.di

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.github.zawadz88.audioservice.CurrentContentIntentProvider
import com.github.zawadz88.audioservice.di.AudioPlayerServiceModule
import com.github.zawadz88.exoplayeraudiosample.internal.di.annotation.ViewModelKey
import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import com.github.zawadz88.exoplayeraudiosample.presentation.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module(includes = [AudioPlayerServiceModule::class])
abstract class MainFeatureContributorModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(userViewModel: MainViewModel): ViewModel

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideCurrentContentIntentProvider(): CurrentContentIntentProvider = object : CurrentContentIntentProvider {
            override fun provideCurrentContentIntent(context: Context) = Intent(context, MainActivity::class.java)
        }
    }
}
