package com.jurajkuliska.eightqueens.game.domain.model

sealed interface QueenPlacementResult {
    data class Conflict(val queen: Queen) : QueenPlacementResult
    enum class Success : QueenPlacementResult {
        Added,
        Removed,
        AddedAndWin,
    }
}