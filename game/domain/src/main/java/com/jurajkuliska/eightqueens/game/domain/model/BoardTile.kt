package com.jurajkuliska.eightqueens.game.domain.model

data class BoardTile(
    val coordinates: Coordinates,
    val isWhite: Boolean,
    val rowNotation: String?,
    val columnNotation: String?,
)