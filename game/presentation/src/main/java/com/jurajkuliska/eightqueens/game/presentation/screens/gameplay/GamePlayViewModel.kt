package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jurajkuliska.eightqueens.game.domain.handler.BoardStateHandler
import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.domain.model.GameType
import com.jurajkuliska.eightqueens.game.domain.model.PiecePlacementResult
import com.jurajkuliska.eightqueens.game.domain.usecase.GetBoardStateHandlerUseCase
import com.jurajkuliska.eightqueens.game.presentation.model.BoardStateUi
import com.jurajkuliska.eightqueens.game.presentation.model.toBoardTileUi
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
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

    private val gameType = when (navArgs.type) {
        GameRoute.GamePlay.Type.Queens -> GameType.Queen(boardSize = navArgs.boardSize)
        GameRoute.GamePlay.Type.Knights -> GameType.Knight(boardSize = navArgs.boardSize)
    }
    private var errorJob: Job? = null
    private val errorTiles = MutableStateFlow<Set<Coordinates>>(emptySet())
    private val boardStateHandler: BoardStateHandler = getBoardStateHandlerUseCase(gameType = gameType)
    val uiState: StateFlow<UiState> = combine(
        boardStateHandler.board,
        errorTiles,
    ) { board, errorTiles ->
        UiState(
            boardState = board.setErrorTiles(
                errorTileCoordinates = errorTiles,
            ),
            totalQueensToPlace = board.totalPiecesToPlace,
            queensLeft = board.queensLeft,
            isWin = board.isWin,
            allSolutionsCount = gameType.allSolutionsCount,
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
            allSolutionsCount = gameType.allSolutionsCount,
        ),
    )

    fun onTileTap(coordinates: Coordinates) {
        cancelErrorTilesAnimation()
        when (val result = boardStateHandler.placePiece(coordinates = coordinates)) {
            is PiecePlacementResult.Conflict -> {
                errorJob = viewModelScope.launch {
                    repeat(3) {
                        delay(200)
                        errorTiles.value = result.conflictingCoordinates + coordinates
                        delay(200)
                        errorTiles.value = emptySet()
                    }
                }
            }

            PiecePlacementResult.Success.Removed,
            PiecePlacementResult.Success.Added,
                -> Unit // no need to do anything
        }
    }

    fun onResetClick() {
        cancelErrorTilesAnimation()
        boardStateHandler.reset()
    }

    fun onPickDifferentBoardClick() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.RestartGame)
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.NavigateBack)
        }
    }

    private fun cancelErrorTilesAnimation() {
        errorJob?.cancel()
        errorTiles.value = emptySet()
    }

    data class UiState(
        val boardState: BoardStateUi,
        val isWin: Boolean,
        val totalQueensToPlace: Int,
        val queensLeft: Int,
        val allSolutionsCount: Int,
    ) {
        val isRestartGameButtonEnabled = totalQueensToPlace > queensLeft
    }

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
        data object RestartGame : UiEvent
    }
}