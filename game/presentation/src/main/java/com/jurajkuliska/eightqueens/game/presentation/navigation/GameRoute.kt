package com.jurajkuliska.eightqueens.game.presentation.navigation

import androidx.annotation.VisibleForTesting
import kotlinx.serialization.Serializable

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
sealed class GameRoute {
    @Serializable
    data object Initial : GameRoute()

    @Serializable
    data class GamePlay(val boardSize: Int, val allSolutionsCount: Int) : GameRoute()
}