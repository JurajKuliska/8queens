package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandler
import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.domain.usecase.GetBoardStateHandlerUseCase
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class GamePlayViewModel(
    navArgs: GameRoute.GamePlay,
    getBoardStateHandlerUseCase: GetBoardStateHandlerUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val boardStateHandler: BoardStateHandler = getBoardStateHandlerUseCase(boardSize = navArgs.boardSize)
    val uiState: StateFlow<UiState> = boardStateHandler.board.map {
        UiState(boardState = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState(BoardState(board = persistentListOf())),
    )

    fun onTileTap(coordinates: Coordinates) {
        boardStateHandler.placeQueen(coordinates = coordinates)
    }

    fun onResetClick() {
        boardStateHandler.reset()
    }

    fun onBackClick() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.NavigateBack)
        }
    }

    data class UiState(
        val boardState: BoardState,
    )

    sealed interface UiEvent {
        data object NavigateBack : UiEvent
    }
}