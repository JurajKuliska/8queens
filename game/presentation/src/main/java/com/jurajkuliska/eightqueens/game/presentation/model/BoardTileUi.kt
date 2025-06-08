package com.jurajkuliska.eightqueens.game.presentation.model

import com.jurajkuliska.eightqueens.game.domain.model.Coordinates

data class BoardTileUi(
    val coordinates: Coordinates,
    val hasQueen: Boolean,
    val isWhite: Boolean,
    val rowNotation: String?,
    val columnNotation: String?,
)
