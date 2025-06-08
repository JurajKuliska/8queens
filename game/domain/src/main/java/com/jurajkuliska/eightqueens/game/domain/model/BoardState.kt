package com.jurajkuliska.eightqueens.game.domain.model

import kotlinx.collections.immutable.ImmutableList

data class BoardState(
    val board: ImmutableList<ImmutableList<BoardTile>>,
    val queensLeft: Int,
)
