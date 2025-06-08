package com.jurajkuliska.eightqueens.game.domain.model

sealed interface QueenPlacementResult {
    data class Conflict(val queen: Queen) : QueenPlacementResult
    data object Success : QueenPlacementResult
}