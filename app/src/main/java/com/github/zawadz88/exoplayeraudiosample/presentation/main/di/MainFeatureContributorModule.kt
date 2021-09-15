package com.github.zawadz88.exoplayeraudiosample.presentation.main.di

import android.content.Context
import android.content.Intent
import com.github.zawadz88.audioservice.CurrentContentIntentProvider
import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
abstract class MainFeatureContributorModule {

    companion object {

        @Provides
        fun provideCurrentContentIntentProvider(): CurrentContentIntentProvider = object : CurrentContentIntentProvider {
            override fun provideCurrentContentIntent(context: Context) = Intent(context, MainActivity::class.java)
        }
    }
}
