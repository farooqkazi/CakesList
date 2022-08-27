package com.assignment.cakeslist.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

abstract class SuspendUseCase<in I, out O>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(i: I): Result<O> {
        return try {
            withContext(dispatcher) {
                val res = execute(i)
                Result.success(res)
            }
        } catch (e: Exception) {
            if (e is SocketTimeoutException) {
                Result.failure(Exception("Could not connect to server, Please try again later!"))
            } else {
                Result.failure(e)

            }
        }
    }

    abstract suspend fun execute(i: I): O
}