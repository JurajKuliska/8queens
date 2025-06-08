package com.jurajkuliska.eightqueens.game.domain.model

data class BoardState(
    val board: List<List<BoardTile>>,
) {
    val totalQueensToPlace = board.size
    val queensLeft = totalQueensToPlace - board.flatten().count { it.hasQueen }
}