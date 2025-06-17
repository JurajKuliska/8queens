package com.jurajkuliska.eightqueens.game.domain.model

data class Knight(
    override val coordinates: Coordinates,
    private val boardSize: Int,
) : Piece {

    private val maxIndex = boardSize - 1

    /**    0  1  2  3  4  5  6  7
     *  7 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  6 [ ][ ][X][ ][X][ ][ ][ ]
     *  5 [ ][X][ ][ ][ ][X][ ][ ]
     *  4 [ ][ ][ ][N][ ][ ][ ][ ]
     *  3 [ ][X][ ][ ][ ][X][ ][ ]
     *  2 [ ][ ][X][ ][X][ ][ ][ ]
     *  1 [ ][ ][ ][ ][ ][ ][ ][ ]
     *  0 [ ][ ][ ][ ][ ][ ][ ][ ]
     */

    /**    0  1  2
     *  2 [N][ ][N]
     *  1 [ ][N][ ]
     *  0 [N][ ][N]
     */

    override val attacking = getAttackingSquares(coordinates)

    private fun getAttackingSquares(coordinates: Coordinates): Set<Coordinates> =
        buildList {
            val upRow = coordinates.rowIndex + 2
            addAll(listOf(Coordinates(upRow, coordinates.columnIndex - 1), Coordinates(upRow, coordinates.columnIndex + 1)))
            val downRow = coordinates.rowIndex - 2
            addAll(listOf(Coordinates(downRow, coordinates.columnIndex - 1), Coordinates(downRow, coordinates.columnIndex + 1)))
            val leftColumn = coordinates.columnIndex - 2
            addAll(listOf(Coordinates(coordinates.rowIndex - 1, leftColumn), Coordinates(coordinates.rowIndex + 1, leftColumn)))
            val rightColumn = coordinates.columnIndex + 2
            addAll(listOf(Coordinates(coordinates.rowIndex - 1, rightColumn), Coordinates(coordinates.rowIndex + 1, rightColumn)))
        }.filter {
            it.rowIndex in (0..maxIndex) && it.columnIndex in (0..maxIndex)
        }.toSet()
}
