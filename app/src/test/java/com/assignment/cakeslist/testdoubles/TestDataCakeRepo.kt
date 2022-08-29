package com.assignment.cakeslist.testdoubles

import com.assignment.cakeslist.data.entity.Cake
import com.assignment.cakeslist.domain.cake.CakeRepository

enum class ScenarioType {
    DEFAULT,
    EMPTY,
    DUPLICATES,
    UNSORTED
}

fun getFakeCakesRepo(types: ScenarioType = ScenarioType.DEFAULT): CakeRepository {
    return when (types) {
        ScenarioType.DUPLICATES -> DuplicateFakeCakeRepository()
        ScenarioType.EMPTY -> EmptyFakeCakeRepository()
        else -> FakeCakeRepository()
    }
}

fun getCakes(types: ScenarioType = ScenarioType.DEFAULT): List<Cake> {
    return when (types) {
        ScenarioType.EMPTY -> emptyList()
        ScenarioType.DUPLICATES -> listOf(
            bananaCake,
            birthDayCake,
            carrotCake,
            lemonCheeseCake,
            bananaCake,
            lemonCheeseCake
        )
        ScenarioType.UNSORTED -> listOf(
            carrotCake,
            birthDayCake,
            bananaCake
        )
        else -> listOf(
            bananaCake,
            birthDayCake,
            carrotCake
        )
    }
}

class EmptyFakeCakeRepository : CakeRepository {
    override suspend fun getCakes() = getCakes(ScenarioType.EMPTY)
}

class FakeCakeRepository : CakeRepository {
    override suspend fun getCakes() = getCakes(ScenarioType.DEFAULT)
}

class DuplicateFakeCakeRepository : CakeRepository {
    override suspend fun getCakes() = getCakes(ScenarioType.DUPLICATES)
}

val bananaCake = Cake(
    title = "Banana cake",
    desc = "Donkey kongs favourite",
    image = "http://example.om/image1"
)

val birthDayCake = Cake(
    title = "Birthday cake",
    desc = "a yearly treat",
    image = "http://example.om/image2"
)

val carrotCake = Cake(
    title = "Carrot cake",
    desc = "Bugs bunny favourite",
    image = "http://example.om/image3"
)

val lemonCheeseCake = Cake(
    title = "Lemon cheesecake",
    desc = "A cheesecake made from lemon",
    image = "http://example.om/image4"
)
