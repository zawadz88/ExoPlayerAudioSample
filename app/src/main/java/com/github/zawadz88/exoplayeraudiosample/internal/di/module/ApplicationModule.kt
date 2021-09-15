package com.github.zawadz88.exoplayeraudiosample.internal.di.module

import androidx.lifecycle.ViewModelProvider
import com.github.zawadz88.exoplayeraudiosample.data.executor.JobExecutor
import com.github.zawadz88.exoplayeraudiosample.domain.executor.PostExecutionThread
import com.github.zawadz88.exoplayeraudiosample.domain.executor.ThreadExecutor
import com.github.zawadz88.exoplayeraudiosample.presentation.UIThread
import com.github.zawadz88.exoplayeraudiosample.presentation.common.DaggerViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}
