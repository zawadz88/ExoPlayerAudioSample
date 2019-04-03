package com.github.zawadz88.exoplayeraudiosample.presentation.main.di

import androidx.lifecycle.ViewModel
import com.github.zawadz88.exoplayeraudiosample.internal.di.annotation.ViewModelKey
import com.github.zawadz88.exoplayeraudiosample.presentation.main.di.MainActivityModule
import com.github.zawadz88.exoplayeraudiosample.presentation.main.view.MainActivity
import com.github.zawadz88.exoplayeraudiosample.presentation.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class MainFeatureContributorModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity
    
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(userViewModel: MainViewModel): ViewModel
}
