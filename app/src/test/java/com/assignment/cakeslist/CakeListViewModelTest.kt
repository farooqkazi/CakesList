package com.assignment.cakeslist

import com.assignment.cakeslist.domain.cake.LoadCakesUseCase
import com.assignment.cakeslist.presentation.cakelist.CakeListViewModel
import com.assignment.cakeslist.presentation.cakelist.CakeListViewModel.UiState
import com.assignment.cakeslist.rules.CoroutineDispatcherRule
import com.assignment.cakeslist.testdoubles.ScenarioType
import com.assignment.cakeslist.testdoubles.getCakes
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class CakeListViewModelTest {

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()

    private val loadCakesUseCase = mockk<LoadCakesUseCase>()
    private lateinit var viewModel: CakeListViewModel

    @Before
    fun setup() {
        viewModel = CakeListViewModel(loadCakesUseCase)
    }

    @Test
    fun `When data is empty`() = runTest {
        coEvery { loadCakesUseCase(Unit) } returns Result.success(getCakes(ScenarioType.EMPTY))

        viewModel.loadCakes()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Success
        assertTrue(value.cakes.isEmpty())
    }

    @Test
    fun `When data loads with no duplicates, verify the data`() = runTest {
        coEvery { loadCakesUseCase(Unit) } returns Result.success(getCakes())

        viewModel.loadCakes()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Success
        assertEquals(3, value.cakes.size)
    }

    @Test
    fun `When data loads with duplicates, verify the data`() = runTest {
        val data = getCakes(ScenarioType.DUPLICATES)
        coEvery { loadCakesUseCase(Unit) } returns Result.success(data)

        viewModel.loadCakes()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Success
        assertEquals(4, value.cakes.size)
        assertEquals(data[0].title, value.cakes[0].title)
        assertEquals(data[3].title, value.cakes[3].title)
    }

    @Test
    fun `When data loads as unsorted list, verify the sorted list`() = runTest {
        val data = getCakes(ScenarioType.UNSORTED)
        coEvery { loadCakesUseCase(Unit) } returns Result.success(data)

        viewModel.loadCakes()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Success
        assertEquals(3, value.cakes.size)
        assertEquals(data[0].title, value.cakes[2].title)
        assertEquals(data[1].title, value.cakes[1].title)
        assertEquals(data[2].title, value.cakes[0].title)
    }

    @Test
    fun `When repo fails with unknown reason, then handle and show unknown error message`() =
        runTest {
            val exception = Exception()
            coEvery { loadCakesUseCase(Unit) } returns Result.failure(exception)

            viewModel.loadCakes()
            advanceUntilIdle()

            val value = viewModel.uiState.value as UiState.Error
            assertEquals(DEFAULT_ERROR_MSG, value.msg)
        }

    @Test
    fun `When repo fails with custom error, then show that error message`() = runTest {
        coEvery { loadCakesUseCase(Unit) } returns Result.failure(Throwable(ERROR_MSG))

        viewModel.loadCakes()
        advanceUntilIdle()

        val value = viewModel.uiState.value as UiState.Error
        assertEquals(ERROR_MSG, value.msg)
    }

    private companion object {
        const val DEFAULT_ERROR_MSG = "Unknown Error"
        const val ERROR_MSG = "Error"
    }
}
