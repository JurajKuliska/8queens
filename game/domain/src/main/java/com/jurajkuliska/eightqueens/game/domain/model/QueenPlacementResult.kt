package com.jurajkuliska.eightqueens.game.domain.model

sealed interface QueenPlacementResult {
    data class Conflict(val conflictingCoordinates: Set<Coordinates>) : QueenPlacementResult
    enum class Success : QueenPlacementResult {
        Added,
        Removed,
    }
}