package com.jurajkuliska.eightqueens.game.domain.testdata

import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates

internal object BoardTileTestData {

    fun getBoardRow3(
        column0: BoardTile = createWhiteBoardTile(coordinates = Coordinates(rowIndex = 3, columnIndex = 0), rowNotation = "4", columnNotation = null),
        column1: BoardTile = createBlackBoardTile(coordinates = Coordinates(rowIndex = 3, columnIndex = 1), rowNotation = null, columnNotation = null),
        column2: BoardTile = createWhiteBoardTile(coordinates = Coordinates(rowIndex = 3, columnIndex = 2), rowNotation = null, columnNotation = null),
        column3: BoardTile = createBlackBoardTile(coordinates = Coordinates(rowIndex = 3, columnIndex = 3), rowNotation = null, columnNotation = null),
    ) = listOf(
        column0,
        column1,
        column2,
        column3,
    )

    fun getBoardRow2(
        column0: BoardTile = createBlackBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 0), rowNotation = "3", columnNotation = null),
        column1: BoardTile = createWhiteBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 1), rowNotation = null, columnNotation = null),
        column2: BoardTile = createBlackBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 2), rowNotation = null, columnNotation = null),
        column3: BoardTile = createWhiteBoardTile(coordinates = Coordinates(rowIndex = 2, columnIndex = 3), rowNotation = null, columnNotation = null),
    ) = listOf(
        column0,
        column1,
        column2,
        column3,
    )

    fun getBoardRow1(
        column0: BoardTile = createWhiteBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 0), rowNotation = "2", columnNotation = null),
        column1: BoardTile = createBlackBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 1), rowNotation = null, columnNotation = null),
        column2: BoardTile = createWhiteBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 2), rowNotation = null, columnNotation = null),
        column3: BoardTile = createBlackBoardTile(coordinates = Coordinates(rowIndex = 1, columnIndex = 3), rowNotation = null, columnNotation = null),
    ) = listOf(
        column0,
        column1,
        column2,
        column3,
    )

    fun getBoardRow0(
        column0: BoardTile = createBlackBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 0), rowNotation = "1", columnNotation = "A"),
        column1: BoardTile = createWhiteBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 1), rowNotation = null, columnNotation = "B"),
        column2: BoardTile = createBlackBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 2), rowNotation = null, columnNotation = "C"),
        column3: BoardTile = createWhiteBoardTile(coordinates = Coordinates(rowIndex = 0, columnIndex = 3), rowNotation = null, columnNotation = "X"),
    ) = listOf(
        column0,
        column1,
        column2,
        column3,
    )

    fun getBoardDefinitionSize4(
        row3: List<BoardTile> = getBoardRow3(),
        row2: List<BoardTile> = getBoardRow2(),
        row1: List<BoardTile> = getBoardRow1(),
        row0: List<BoardTile> = getBoardRow0(),
    ) = listOf(
        row3,
        row2,
        row1,
        row0,
    )

    internal fun createWhiteBoardTile(
        coordinates: Coordinates,
        rowNotation: String?,
        columnNotation: String?,
    ) = BoardTile(
        coordinates = coordinates,
        rowNotation = rowNotation,
        columnNotation = columnNotation,
        isLight = true,
        hasPiece = false,
    )

    internal fun createBlackBoardTile(
        coordinates: Coordinates,
        rowNotation: String?,
        columnNotation: String?,
    ) = BoardTile(
        coordinates = coordinates,
        rowNotation = rowNotation,
        columnNotation = columnNotation,
        isLight = false,
        hasPiece = false,
    )
}