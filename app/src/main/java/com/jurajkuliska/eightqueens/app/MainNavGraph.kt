package com.jurajkuliska.eightqueens.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jurajkuliska.eightqueens.game.presentation.navigation.gameNavGraph
import com.jurajkuliska.eightqueens.navigation.NavigableGraph

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: NavigableGraph = NavigableGraph.Game,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        gameNavGraph(navController)
    }
}