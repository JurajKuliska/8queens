package com.jurajkuliska.eightqueens.game.presentation.screens.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jurajkuliska.eightqueens.game.presentation.model.BoardSize
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class InitialViewModel : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onBoardSizePicked(boardSize: BoardSize) {

    }

    fun onNext() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.Next)
        }
    }

    sealed interface UiEvent {
        data object Next : UiEvent
    }
}