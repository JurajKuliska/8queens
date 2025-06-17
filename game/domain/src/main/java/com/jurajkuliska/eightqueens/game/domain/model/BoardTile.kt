package com.jurajkuliska.eightqueens.game.domain.model

data class BoardTile(
    val coordinates: Coordinates,
    val isLight: Boolean,
    val rowNotation: String?,
    val columnNotation: String?,
    val hasPiece: Boolean,
)