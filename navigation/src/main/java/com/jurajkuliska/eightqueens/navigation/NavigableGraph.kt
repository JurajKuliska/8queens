package com.jurajkuliska.eightqueens.navigation

import kotlinx.serialization.Serializable

sealed class NavigableGraph : Navigable {
    @Serializable
    data object Game : NavigableGraph()
}