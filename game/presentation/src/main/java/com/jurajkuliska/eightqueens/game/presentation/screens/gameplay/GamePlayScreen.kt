package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.presentation.R
import com.jurajkuliska.eightqueens.game.presentation.model.BoardStateUi
import com.jurajkuliska.eightqueens.game.presentation.model.BoardTileUi
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import com.jurajkuliska.eightqueens.ui.components.EightQueensScaffold
import com.jurajkuliska.eightqueens.ui.theme.PieceColor
import com.jurajkuliska.eightqueens.ui.theme.Spacing
import com.jurajkuliska.eightqueens.ui.theme.TileDark
import com.jurajkuliska.eightqueens.ui.theme.TileLight
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import com.jurajkuliska.eightqueens.ui.R as uiR

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
            state = uiState.boardState,
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
    state: BoardStateUi,
    onTileTap: (Coordinates) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.aspectRatio(ratio = 1f)) {
        state.board.forEach { row ->
            Row(
                modifier = Modifier.weight(1f)
            ) {
                row.forEach { tile ->
                    Tile(
                        modifier = Modifier
                            .aspectRatio(ratio = 1f)
                            .weight(1f),
                        tile = tile,
                        onClick = { onTileTap(tile.coordinates) },
                    )
                }
            }
        }
    }
}

@Composable
private fun Tile(
    modifier: Modifier = Modifier,
    tile: BoardTileUi,
    onClick: () -> Unit,
) {
    val queenSizeAnimation = animateFloatAsState(targetValue = if (tile.hasQueen) 1f else 0f)
    Box(
        modifier = modifier
            .background(color = if (tile.isLight) TileLight else TileDark)
            .clickable {
                onClick()
            },
    ) {
        tile.columnNotation?.let {
            Notation(text = it, isOnLight = tile.isLight, modifier = Modifier.align(Alignment.BottomEnd))
        }
        tile.rowNotation?.let {
            Notation(text = it, isOnLight = tile.isLight, modifier = Modifier.align(Alignment.TopStart))
        }
        Icon(
            modifier = Modifier
                .padding(all = Spacing.xxS)
                .align(Alignment.Center)
                .fillMaxSize(fraction = queenSizeAnimation.value),
            painter = painterResource(id = uiR.drawable.chess_queen),
            tint = PieceColor,
            contentDescription = stringResource(id = R.string.general_queen_content_description),
        )
    }
}

@Composable
private fun Notation(
    text: String,
    isOnLight: Boolean,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(all = Spacing.xxxS),
        text = text,
        color = if (isOnLight) TileDark else TileLight,
        style = MaterialTheme.typography.labelSmall,
    )
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
