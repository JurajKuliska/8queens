package com.jurajkuliska.eightqueens.game.domain.usecase

import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates

internal interface CreateBoardUseCase {
    operator fun invoke(boardSize: Int): List<List<BoardTile>>
}

internal class CreateBoardUseCaseImpl(
    private val getColumnNotationUseCase: GetColumnNotationUseCase,
) : CreateBoardUseCase {

    override operator fun invoke(boardSize: Int): List<List<BoardTile>> =
        (boardSize - 1 downTo 0).map { rowIndex ->
            (0 until boardSize).map { columnIndex ->
                BoardTile(
                    coordinates = Coordinates(rowIndex = rowIndex, columnIndex = columnIndex),
                    isLight = isWhite(boardSize = boardSize, rowIndex = rowIndex, columnIndex = columnIndex),
                    rowNotation = takeIf { columnIndex == 0 }?.let { (rowIndex + 1).toString() },
                    columnNotation = takeIf { rowIndex == 0 }?.let { getColumnNotationUseCase(columnIndex = columnIndex) }?.toString(),
                    hasQueen = false,
                )
            }
        }

    private fun isWhite(
        boardSize: Int,
        rowIndex: Int,
        columnIndex: Int,
    ): Boolean {
        val modSum = (columnIndex + rowIndex).mod(2)
        return if (boardSize.mod(2) == 0) {
            modSum == 1
        } else {
            modSum == 0
        }
    }
}
