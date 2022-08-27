package com.assignment.cakeslist.presentation.cakedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.cakeslist.data.entity.Cake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class CakeDetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    val cake: Flow<Cake> = flow {
        val args = CakeDetailFragmentArgs.fromSavedStateHandle(stateHandle)
        emit(args.cake)
    }.shareIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), replay = 1)
}
