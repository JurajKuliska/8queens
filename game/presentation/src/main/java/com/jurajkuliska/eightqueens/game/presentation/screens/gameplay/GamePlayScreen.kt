package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import com.jurajkuliska.eightqueens.ui.components.EightQueensScaffold
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun GamePlayScreen(
    navArgs: GameRoute.GamePlay,
    onNext: () -> Unit,
) {
    val viewModel = koinViewModel<GamePlayViewModel> { parametersOf(navArgs) }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    EightQueensScaffold {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Game started ${uiState.boardSize}")
            Button(
                onClick = onNext,
            ) {
                Text(text = "Click to Win!")
            }
        }
    }
}
