package com.jurajkuliska.eightqueens.game.domain.handler

import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.domain.model.GameType
import com.jurajkuliska.eightqueens.game.domain.model.PiecePlacementResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface BoardStateHandler {

    val board: Flow<BoardState>

    fun placePiece(coordinates: Coordinates): PiecePlacementResult

    fun reset()
}

internal class BoardStateHandlerImpl(
    private val gameType: GameType,
    private val boardDefinition: List<List<BoardTile>>,
) : BoardStateHandler {

    private val occupiedCoordinates = MutableStateFlow<Set<Coordinates>>(emptySet())

    override val board: Flow<BoardState> = occupiedCoordinates.map { queens ->

        BoardState(
            board = boardDefinition.map {
                it.map { tile ->
                    tile.copy(hasPiece = occupiedCoordinates.value.contains(tile.coordinates))
                }
            },
            totalPiecesToPlace = gameType.totalPiecesToPlace,
        )
    }

    override fun placePiece(coordinates: Coordinates): PiecePlacementResult =
        if (coordinates.hasPiece()) {
            occupiedCoordinates.value = occupiedCoordinates.value.filterNot { it == coordinates }.toSet()
            PiecePlacementResult.Success.Removed
        } else {
            if (coordinates.canPlacePiece()) {
                occupiedCoordinates.value = occupiedCoordinates.value + coordinates
                PiecePlacementResult.Success.Added
            } else {
                val conflictingCoordinates = occupiedCoordinates.value.filter {
                    gameType.attacking(it).contains(it)
                }.toSet()
                PiecePlacementResult.Conflict(conflictingCoordinates = conflictingCoordinates)
            }
        }

    override fun reset() {
        occupiedCoordinates.value = emptySet()
    }

    private fun Coordinates.hasPiece() = occupiedCoordinates.value.contains(this)

    private fun Coordinates.canPlacePiece() =
        !occupiedCoordinates.value.map { gameType.attacking(it) }.flatten().toSet().contains(this)
}