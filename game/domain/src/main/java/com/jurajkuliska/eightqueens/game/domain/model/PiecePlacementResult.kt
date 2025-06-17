package com.jurajkuliska.eightqueens.game.domain.model

sealed interface PiecePlacementResult {
    data class Conflict(val conflictingCoordinates: Set<Coordinates>) : PiecePlacementResult
    enum class Success : PiecePlacementResult {
        Added,
        Removed,
    }
}