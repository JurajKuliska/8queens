package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandler
import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.domain.model.QueenPlacementResult
import com.jurajkuliska.eightqueens.game.domain.usecase.GetBoardStateHandlerUseCase
import com.jurajkuliska.eightqueens.game.presentation.model.BoardStateUi
import com.jurajkuliska.eightqueens.game.presentation.model.toBoardTileUi
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class GamePlayViewModel(
    navArgs: GameRoute.GamePlay,
    getBoardStateHandlerUseCase: GetBoardStateHandlerUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val errorTiles = MutableStateFlow<Set<Coordinates>>(emptySet())
    private val isWin = MutableStateFlow(false)
    private val boardStateHandler: BoardStateHandler = getBoardStateHandlerUseCase(boardSize = navArgs.boardSize)
    val uiState: StateFlow<UiState> = combine(
        boardStateHandler.board,
        errorTiles,
        isWin,
    ) { board, errorTiles, isWin ->
        UiState(
            boardState = board.setErrorTiles(
                errorTileCoordinates = errorTiles,
            ),
            totalQueensToPlace = board.totalQueensToPlace,
            queensLeft = board.queensLeft,
            isWin = isWin,
            allSolutionsCount = navArgs.allSolutionsCount,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState(
            boardState = BoardStateUi(
                board = persistentListOf(),
            ),
            totalQueensToPlace = 0,
            queensLeft = 0,
            isWin = false,
            allSolutionsCount = navArgs.allSolutionsCount,
        ),
    )

    fun onTileTap(coordinates: Coordinates) {
        when (val result = boardStateHandler.placeQueen(coordinates = coordinates)) {
            is QueenPlacementResult.Conflict -> viewModelScope.launch {
                val tilesWithErrors = result.queen.let { it.attacking + it.coordinates }
                (0..2).forEach {
                    delay(200)
                    errorTiles.value = tilesWithErrors
                    delay(200)
                    errorTiles.value = emptySet()
                }
            }

            QueenPlacementResult.Success.AddedAndWin -> isWin.value = true
            QueenPlacementResult.Success.Removed -> isWin.value = false
            QueenPlacementResult.Success.Added -> Unit // no need to do anything
        }
    }

    fun onResetClick() {
        isWin.value = false
        boardStateHandler.reset()
    }

    fun onPickDifferentBoardClick() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.NavigateBack)
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.NavigateBack)
        }
    }

    data class UiState(
        val boardState: BoardStateUi,
        val isWin: Boolean,
        val totalQueensToPlace: Int,
        val queensLeft: Int,
        val allSolutionsCount: Int,
    )

    private fun BoardState.setErrorTiles(errorTileCoordinates: Set<Coordinates>) =
        BoardStateUi(
            board = board.map { row ->
                row.map {
                    it.toBoardTileUi(isError = errorTileCoordinates.contains(it.coordinates))
                }.toPersistentList()
            }.toPersistentList(),
        )

    sealed interface UiEvent {
        data object NavigateBack : UiEvent
    }
}