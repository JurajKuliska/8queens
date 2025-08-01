package com.jurajkuliska.eightqueens.game.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.jurajkuliska.eightqueens.game.presentation.screens.gameplay.GamePlayScreen
import com.jurajkuliska.eightqueens.game.presentation.screens.initial.InitialScreen
import com.jurajkuliska.eightqueens.navigation.NavigableGraph

fun NavGraphBuilder.gameNavGraph(navController: NavController) =
    navigation<NavigableGraph.Game>(
        startDestination = GameRoute.Initial,
    ) {
        composable<GameRoute.Initial> {
            InitialScreen(
                onNext = { navController.navigate(route = it) },
            )
        }
        composable<GameRoute.GamePlay> { backStackEntry ->
            val navArgs: GameRoute.GamePlay = backStackEntry.toRoute()
            GamePlayScreen(
                navArgs = navArgs,
                onBack = { navController.popBackStack() },
                onRestartGame = { navController.popBackStack(GameRoute.Initial, inclusive = false) },
            )
        }
    }