package com.github.zawadz88.exoplayeraudiosample.internal.di.module

import com.github.zawadz88.exoplayeraudiosample.data.executor.JobExecutor
import com.github.zawadz88.exoplayeraudiosample.domain.executor.PostExecutionThread
import com.github.zawadz88.exoplayeraudiosample.domain.executor.ThreadExecutor
import com.github.zawadz88.exoplayeraudiosample.presentation.UIThread
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
}
