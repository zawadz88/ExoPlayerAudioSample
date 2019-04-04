package com.github.zawadz88.exoplayeraudiosample.domain.interactor

import com.github.zawadz88.exoplayeraudiosample.domain.executor.PostExecutionThread
import com.github.zawadz88.exoplayeraudiosample.domain.executor.ThreadExecutor
import com.github.zawadz88.exoplayeraudiosample.domain.interactor.base.SingleUseCase
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoadPlaylist
@Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<String>, Unit>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<String>> =
        Single.fromCallable { (1..3).map { "Song no. $it" } }.delay(2, TimeUnit.SECONDS)
}
