package com.assignment.cakeslist.presentation.cakelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.cakeslist.data.entity.Cake
import com.assignment.cakeslist.domain.cake.LoadCakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakeListViewModel @Inject constructor(
    private val loadCakesUseCase: LoadCakesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    init {
        loadCakes()
    }

    private var loadCakesJob: Job? = null
    fun loadCakes() {
        loadCakesJob?.cancel()
        if (_uiState.value !is UiState.Loading) {
            _uiState.value = UiState.Loading
        }
        loadCakesJob = viewModelScope.launch {
            val result: Result<List<Cake>> = loadCakesUseCase(Unit)
            result.onSuccess { cakes ->
                val distinctAndSorted: List<Cake> =
                    cakes.distinctBy { it.title }.sortedBy { it.title }
                _uiState.value = UiState.Success(distinctAndSorted)
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Unknown Error")

            }
        }

    }


    sealed class UiState {
        object Loading : UiState()
        class Error(val msg: String) : UiState()
        class Success(val cakes: List<Cake>) : UiState()
    }
}