package com.jurajkuliska.eightqueens.game.presentation.model

import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates

internal data class BoardTileUi(
    val coordinates: Coordinates,
    val isLight: Boolean,
    val rowNotation: String?,
    val columnNotation: String?,
    val isError: Boolean,
    val hasQueen: Boolean,
) {
    val showQueen: Boolean = hasQueen || isError
}

internal fun BoardTile.toBoardTileUi(isError: Boolean) = BoardTileUi(
    coordinates = coordinates,
    isLight = isLight,
    rowNotation = rowNotation,
    columnNotation = columnNotation,
    hasQueen = hasPiece,
    isError = isError,
)
