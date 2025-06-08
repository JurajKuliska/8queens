package com.jurajkuliska.eightqueens.game.domain.model

import kotlin.math.min

data class Queen(
    val coordinates: Coordinates,
    private val boardSize: Int,
) {
    private val maxIndex = boardSize - 1

    val attacking: Set<Coordinates> =
        rowCoordinates() + columnCoordinates() + diagonalCoordinates() - coordinates

    private fun rowCoordinates() = (0 until boardSize).map {
        Coordinates(rowIndex = coordinates.rowIndex, columnIndex = it)
    }.toSet()

    private fun columnCoordinates() = (0 until boardSize).map {
        Coordinates(rowIndex = it, columnIndex = coordinates.columnIndex)
    }.toSet()

    private fun diagonalCoordinates(): Set<Coordinates> {
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
