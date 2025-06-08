package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.lifecycle.ViewModel
import com.jurajkuliska.eightqueens.game.domain.usecase.CreateBoardUseCase
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import kotlinx.coroutines.flow.MutableStateFlow

internal class GamePlayViewModel(
    navArgs: GameRoute.GamePlay,
    private val createBoardUseCase: CreateBoardUseCase,
) : ViewModel() {

    val uiState = MutableStateFlow(UiState(navArgs.boardSize))

    data class UiState(
        private val boardSize: Int,
    ) {
//        val board: ImmutableList<ImmutableList<BoardTileUi>> = createBoardUseCase(boardSize = boardSize)
    }
}