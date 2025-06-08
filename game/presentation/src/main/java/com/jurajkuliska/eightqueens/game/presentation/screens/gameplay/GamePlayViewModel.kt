package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.lifecycle.ViewModel
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import kotlinx.coroutines.flow.MutableStateFlow

internal class GamePlayViewModel(
    navArgs: GameRoute.GamePlay,
) : ViewModel() {

    val uiState = MutableStateFlow(UiState(navArgs.boardSize))

    data class UiState(val boardSize: Int)
}