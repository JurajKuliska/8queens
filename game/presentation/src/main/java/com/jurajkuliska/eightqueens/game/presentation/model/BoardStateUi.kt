package com.jurajkuliska.eightqueens.game.presentation.model

import kotlinx.collections.immutable.ImmutableList

internal data class BoardStateUi(
    val board: ImmutableList<ImmutableList<BoardTileUi>>,
    val totalQueensToPlace: Int,
    val queensLeft: Int,
)
