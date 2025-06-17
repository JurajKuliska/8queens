package com.jurajkuliska.eightqueens.game.domain.model

data class BoardState(
    val board: List<List<BoardTile>>,
    val totalPiecesToPlace: Int,
) {
    val queensLeft = totalPiecesToPlace - board.flatten().count { it.hasPiece }
    val isWin = queensLeft == 0
}