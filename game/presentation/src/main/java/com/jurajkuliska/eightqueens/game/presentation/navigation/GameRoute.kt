package com.jurajkuliska.eightqueens.game.presentation.navigation

import kotlinx.serialization.Serializable

internal sealed class GameRoute {
    @Serializable
    data object Initial : GameRoute()

    @Serializable
    data object Main : GameRoute()

    @Serializable
    data class Congratulations(val message: String) : GameRoute()
}