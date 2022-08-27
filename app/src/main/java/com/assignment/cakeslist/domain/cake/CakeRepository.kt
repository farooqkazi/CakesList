package com.assignment.cakeslist.domain.cake

import com.assignment.cakeslist.data.entity.Cake

interface CakeRepository {
    suspend fun getCakes(): List<Cake>
}