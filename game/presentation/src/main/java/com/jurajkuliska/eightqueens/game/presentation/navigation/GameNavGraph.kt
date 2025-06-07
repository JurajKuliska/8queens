package com.jurajkuliska.eightqueens.game.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.jurajkuliska.eightqueens.game.presentation.screens.congratulations.CongratulationsScreen
import com.jurajkuliska.eightqueens.game.presentation.screens.initial.InitialScreen
import com.jurajkuliska.eightqueens.game.presentation.screens.main.MainScreen
import com.jurajkuliska.eightqueens.navigation.NavigableGraph

fun NavGraphBuilder.gameNavGraph(navController: NavController) =
    navigation<NavigableGraph.Game>(
        startDestination = GameRoute.Initial,
    ) {
        composable<GameRoute.Initial> {
            InitialScreen(
                onNext = { navController.navigate(GameRoute.Main) },
            )
        }
        composable<GameRoute.Main> {
            MainScreen(
                onNext = { navController.navigate(GameRoute.Congratulations(message = "Congrats!")) },
            )
        }
        composable<GameRoute.Congratulations> { backStackEntry ->
            val navArgs: GameRoute.Congratulations = backStackEntry.toRoute()
            CongratulationsScreen(
                navArgs = navArgs,
                onBack = { navController.popBackStack() },
                onFinish = {
                    navController.popBackStack(
                        route = GameRoute.Initial,
                        inclusive = false
                    )
                }
            )
        }
    }