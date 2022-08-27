package com.assignment.cakeslist.domain.cake

import com.assignment.cakeslist.data.entity.Cake
import com.assignment.cakeslist.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import com.assignment.cakeslist.domain.SuspendUseCase
import javax.inject.Inject

class LoadCakesUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val cakeRepository: CakeRepository
) : SuspendUseCase<Unit, List<Cake>>(dispatcher) {
    override suspend fun execute(i: Unit): List<Cake> {
        return cakeRepository.getCakes()
    }
}