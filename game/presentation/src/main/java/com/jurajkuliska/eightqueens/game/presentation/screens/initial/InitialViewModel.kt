package com.jurajkuliska.eightqueens.game.presentation.screens.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jurajkuliska.eightqueens.game.presentation.model.BoardSize
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class InitialViewModel : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val isBoardSizePickerOpened = MutableStateFlow(false)
    private val boardSize = MutableStateFlow(BoardSize.EightByEight)

    val uiState = combine(
        isBoardSizePickerOpened,
        boardSize,
    ) { isBoardSizePickerOpened, boardSize ->
        UiState(
            boardSizePickerState = UiState.BoardSizePickerState(
                isExpanded = isBoardSizePickerOpened,
                selectedOption = boardSize,
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState(
            boardSizePickerState = UiState.BoardSizePickerState(
                isExpanded = isBoardSizePickerOpened.value,
                selectedOption = boardSize.value
            )
        )
    )

    fun onBoardSizePicked(boardSize: BoardSize) {
        isBoardSizePickerOpened.value = false
        this.boardSize.value = boardSize
    }

    fun onBoardSizePickerDismiss() {
        isBoardSizePickerOpened.value = false
    }

    fun onBoardSizePickerOpen() {
        isBoardSizePickerOpened.value = true
    }

    fun onNext() {
        viewModelScope.launch {
            _uiEvent.emit(
                UiEvent.NavigateToGamePlay(
                    route = GameRoute.GamePlay(
                        boardSize = boardSize.value.size,
                        allSolutionsCount = boardSize.value.allSolutionsCount
                    )
                )
            )
        }
    }

    data class UiState(
        val boardSizePickerState: BoardSizePickerState,
    ) {
        data class BoardSizePickerState(
            val isExpanded: Boolean,
            val selectedOption: BoardSize,
        ) {
            val options = BoardSize.entries
        }
    }

    sealed interface UiEvent {
        data class NavigateToGamePlay(val route: GameRoute.GamePlay) : UiEvent
    }
}