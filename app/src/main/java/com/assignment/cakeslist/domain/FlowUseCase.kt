package com.assignment.cakeslist.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in I, out O>(private val dispatcher: CoroutineDispatcher) {
    operator fun invoke(i: I): Flow<Result<O>> {
        return execute(i)
            .catch { emit(Result.failure(Exception(it))) }
            .flowOn(dispatcher)

    }

    abstract fun execute(i: I): Flow<Result<O>>
}