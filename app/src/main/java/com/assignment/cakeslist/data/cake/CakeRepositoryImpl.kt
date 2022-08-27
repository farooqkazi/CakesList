package com.assignment.cakeslist.data.cake

import com.assignment.cakeslist.data.CakesApi
import com.assignment.cakeslist.data.entity.Cake
import com.assignment.cakeslist.domain.cake.CakeRepository
import javax.inject.Inject

class CakeRepositoryImpl @Inject constructor(
    private val cakesAPi: CakesApi
) : CakeRepository {
    override suspend fun getCakes(): List<Cake> {
        return cakesAPi.getCakeList()
    }
}