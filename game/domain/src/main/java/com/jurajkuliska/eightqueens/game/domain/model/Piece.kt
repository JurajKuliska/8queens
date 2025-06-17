package com.jurajkuliska.eightqueens.game.domain.model

interface Piece {

    val coordinates: Coordinates
    val attacking: Set<Coordinates>
}