package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jurajkuliska.eightqueens.game.domain.model.BoardState
import com.jurajkuliska.eightqueens.game.domain.model.BoardTile
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.presentation.R
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import com.jurajkuliska.eightqueens.ui.components.EightQueensScaffold
import com.jurajkuliska.eightqueens.ui.theme.Spacing
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun GamePlayScreen(
    navArgs: GameRoute.GamePlay,
    onBack: () -> Unit,
) {
    val viewModel = koinViewModel<GamePlayViewModel> { parametersOf(navArgs) }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    UiEventObserver(
        uiEventFlow = viewModel.uiEvent,
        onNavigateBack = onBack,
    )

    EightQueensScaffold(
        topBar = {
            TopBar(onBackClick = viewModel::onBackClick)
        }
    ) {
        Content(
            modifier = Modifier.align(Alignment.Center),
            uiState = uiState,
            onTileTap = viewModel::onTileTap,
            onResetClick = viewModel::onResetClick,
        )
    }
}

@Composable
private fun Content(
    uiState: GamePlayViewModel.UiState,
    onTileTap: (Coordinates) -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(all = Spacing.xxL),
            text = stringResource(id = R.string.gameplay_screen_instructions, uiState.boardState.totalQueensToPlace),
            textAlign = TextAlign.Center,
        )
        Board(
            modifier = Modifier.padding(all = Spacing.M),
            boardState = uiState.boardState,
            onTileTap = onTileTap,
        )
        Text(
            modifier = Modifier
                .padding(all = Spacing.xxL)
                .align(alignment = Alignment.CenterHorizontally),
            text = stringResource(id = R.string.gameplay_screen_queens_left, uiState.boardState.queensLeft)
        )
        Button(
            modifier = Modifier
                .padding(top = Spacing.xL)
                .align(alignment = Alignment.CenterHorizontally),
            onClick = onResetClick,
        ) {
            Text(text = stringResource(id = R.string.gameplay_screen_restart_game))
        }
    }
}

@Composable
private fun Board(
    boardState: BoardState,
    onTileTap: (Coordinates) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .aspectRatio(ratio = 1f)
        .background(Color.Red)
    ) {

    }
}

@Composable
private fun Tile(
    boardTile: BoardTile,
    onClick: () -> Unit,
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.general_back_button_content_description)
                )
            }
        }
    )
}

@Composable
private fun UiEventObserver(
    uiEventFlow: Flow<GamePlayViewModel.UiEvent>,
    onNavigateBack: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        uiEventFlow.collect { uiEvent ->
            when (uiEvent) {
                is GamePlayViewModel.UiEvent.NavigateBack -> onNavigateBack()
            }
        }
    }
}
