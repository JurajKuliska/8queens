package com.jurajkuliska.eightqueens.game.domain.model

import kotlinx.serialization.Serializable
import kotlin.math.ceil
import kotlin.math.min

@Serializable
sealed interface GameType {

    val allSolutionsCount: Int
    val totalPiecesToPlace: Int
    fun attacking(coordinates: Coordinates): Set<Coordinates>
    val boardSize: Int
    val maxIndex: Int
        get() = boardSize - 1

    @Serializable
    data class Queen(override val boardSize: Int) : GameType {
        override val totalPiecesToPlace: Int = boardSize

        override fun attacking(coordinates: Coordinates): Set<Coordinates> =
            rowCoordinates(coordinates) + columnCoordinates(coordinates) + diagonalCoordinates(coordinates) - coordinates

        override val allSolutionsCount: Int = when (boardSize) {
            1 -> 1
            2 -> 0
            3 -> 0
            4 -> 2
            5 -> 10
            6 -> 4
            7 -> 40
            8 -> 92
            else -> throw IllegalArgumentException("Illegal board size")
        }

        private fun rowCoordinates(coordinates: Coordinates) = (0 until boardSize).map {
            Coordinates(rowIndex = coordinates.rowIndex, columnIndex = it)
        }.toSet()

        private fun columnCoordinates(coordinates: Coordinates) = (0 until boardSize).map {
            Coordinates(rowIndex = it, columnIndex = coordinates.columnIndex)
        }.toSet()

        private fun diagonalCoordinates(coordinates: Coordinates): Set<Coordinates> {
            val bottomLeft = (1..min(coordinates.rowIndex, coordinates.columnIndex)).map {
                Coordinates(rowIndex = coordinates.rowIndex - it, columnIndex = coordinates.columnIndex - it)
            }.toSet()
            val topLeft = (1..min(maxIndex - coordinates.rowIndex, coordinates.columnIndex)).map {
                Coordinates(rowIndex = coordinates.rowIndex + it, columnIndex = coordinates.columnIndex - it)
            }.toSet()
            val bottomRight = (1..min(coordinates.rowIndex, maxIndex - coordinates.columnIndex)).map {
                Coordinates(rowIndex = coordinates.rowIndex - it, columnIndex = coordinates.columnIndex + it)
            }.toSet()
            val topRight = (1..min(maxIndex - coordinates.rowIndex, maxIndex - coordinates.columnIndex)).map {
                Coordinates(rowIndex = coordinates.rowIndex + it, columnIndex = coordinates.columnIndex + it)
            }.toSet()
            return bottomLeft + topLeft + bottomRight + topRight
        }
    }

    @Serializable
    data class Knight(override val boardSize: Int) : GameType {
        override val totalPiecesToPlace: Int = ceil(boardSize * boardSize / 2.0).toInt()

        override val allSolutionsCount: Int = when (boardSize) {
            1 -> 1
            2 -> 0
            3 -> 0
            4 -> 2
            5 -> 10
            6 -> 4
            7 -> 40
            8 -> 69
            else -> throw IllegalArgumentException("Illegal board size")
        }

        override fun attacking(coordinates: Coordinates): Set<Coordinates> =
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
}