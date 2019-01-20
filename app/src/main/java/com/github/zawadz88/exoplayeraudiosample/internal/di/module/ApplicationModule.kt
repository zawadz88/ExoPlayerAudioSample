package com.github.zawadz88.exoplayeraudiosample.internal.di.module

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.github.zawadz88.exoplayeraudiosample.data.executor.JobExecutor
import com.github.zawadz88.exoplayeraudiosample.domain.executor.PostExecutionThread
import com.github.zawadz88.exoplayeraudiosample.domain.executor.ThreadExecutor
import com.github.zawadz88.exoplayeraudiosample.internal.application.AndroidApplication
import com.github.zawadz88.exoplayeraudiosample.presentation.UIThread
import com.github.zawadz88.exoplayeraudiosample.presentation.common.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindApplication(app: AndroidApplication): Application

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}
