package com.jurajkuliska.eightqueens.game.domain.handler

import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.domain.model.Queen
import com.jurajkuliska.eightqueens.game.domain.model.QueenPlacementResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface BoardStateHandler {

    val board: Flow<BoardState>

    fun placeQueen(coordinates: Coordinates): QueenPlacementResult

    fun reset()
}

internal class BoardStateHandlerImpl(
    private val boardSize: Int,
    private val boardDefinition: List<List<BoardTile>>,
) : BoardStateHandler {

    private val queens = MutableStateFlow<Set<Queen>>(emptySet())

    override val board: Flow<BoardState> = queens.map { queens ->
        val queensCoordinates = queens.map { it.coordinates }.toSet()

        BoardState(
            board = boardDefinition.map {
                it.map { tile ->
                    tile.copy(hasQueen = queensCoordinates.contains(tile.coordinates))
                }
            },
        )
    }

    override fun placeQueen(coordinates: Coordinates): QueenPlacementResult =
        if (coordinates.hasQueen()) {
            queens.value = queens.value.filterNot { it.coordinates == coordinates }.toSet()
            QueenPlacementResult.Success.Removed
        } else {
            val queen = Queen(coordinates = coordinates, boardSize = boardSize)
            if (coordinates.canPlaceQueen()) {
                queens.value = queens.value + queen
                QueenPlacementResult.Success.Added
            } else {
                val conflictingQueensCoordinates = queens.value.filter { it.attacking.contains(coordinates) }.map { it.coordinates }.toSet()
                QueenPlacementResult.Conflict(conflictingCoordinates = conflictingQueensCoordinates)
            }
        }

    override fun reset() {
        queens.value = emptySet()
    }

    private fun Coordinates.hasQueen() = queens.value.map { it.coordinates }.contains(this)

    private fun Coordinates.canPlaceQueen() =
        !queens.value.map { it.attacking }.flatten().toSet().contains(this)
}