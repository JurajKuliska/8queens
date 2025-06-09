package com.jurajkuliska.eightqueens.game.presentation.screens.gameplay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import com.jurajkuliska.eightqueens.ui.R as uiR

private const val ERROR_ANIMATION_LENGTH = 50

@Composable
internal fun GamePlayScreen(
    navArgs: GameRoute.GamePlay,
    onBack: () -> Unit,
    onRestartGame: () -> Unit,
) {
    val viewModel = koinViewModel<GamePlayViewModel> { parametersOf(navArgs) }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    UiEventObserver(
        uiEventFlow = viewModel.uiEvent,
        onNavigateBack = onBack,
        onRestartGame = onRestartGame,
    )

    EightQueensScaffold(
        topBar = {
            TopBar(onBackClick = viewModel::onBackClick)
        }
    ) {
        var initialAlpha by remember { mutableFloatStateOf(0f) }
        val initialAlphaAnimation = animateFloatAsState(initialAlpha)
        var initialOffset by remember { mutableStateOf(40.dp) }
        val initialOffsetAnimation = animateDpAsState(initialOffset)

        LaunchedEffect(key1 = Unit) {
            delay(500)
            initialAlpha = 1f
            initialOffset = 0.dp
        }

        val contentDataHolder = ContentDataHolder(
            uiState = uiState,
            initialAlphaAnimationValue = initialAlphaAnimation.value,
            initialOffsetAnimationValue = initialOffsetAnimation.value,
            onTileTap = viewModel::onTileTap,
            onResetClick = viewModel::onResetClick,
            onPickDifferentBoardClick = viewModel::onPickDifferentBoardClick,
        )
        Box {
            contentDataHolder.PortraitContent(

            )
            if (uiState.isWin) {
                CelebrationOverlay()
            }
        }
    }
}

@Composable
private fun ContentDataHolder.PortraitContent(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.height(150.dp)) {
            if (!uiState.isWin) {
                InstructionsText(
                    modifier = Modifier.align(Alignment.Center),
                    totalQueensToPlace = uiState.totalQueensToPlace,
                    initialAlphaAnimationValue = initialAlphaAnimationValue,
                    initialOffsetAnimationValue = initialOffsetAnimationValue,
                )
            }
            WinningText(
                modifier = Modifier.align(Alignment.Center),
                isWin = uiState.isWin,
                allSolutionsCount = uiState.allSolutionsCount
            )
        }
        Board(
            modifier = Modifier.padding(all = Spacing.M),
            state = uiState.boardState,
            onTileTap = onTileTap,
        )
        if (!uiState.isWin) {
            QueensLeftText(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                queensLeft = uiState.queensLeft,
                initialAlphaAnimationValue = initialAlphaAnimationValue,
                initialOffsetAnimationValue = initialOffsetAnimationValue,
            )
        }
        ControlButtons(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            isRestartGameButtonEnabled = uiState.isRestartGameButtonEnabled,
            initialAlphaAnimationValue = initialAlphaAnimationValue,
            initialOffsetAnimationValue = initialOffsetAnimationValue,
            onResetClick = onResetClick,
            onPickDifferentBoardClick = onPickDifferentBoardClick,
        )
    }
}

@Composable
private fun InstructionsText(
    modifier: Modifier = Modifier,
    totalQueensToPlace: Int,
    initialAlphaAnimationValue: Float,
    initialOffsetAnimationValue: Dp,
) {
    Text(
        modifier = modifier
            .padding(horizontal = Spacing.xxL)
            .alpha(alpha = initialAlphaAnimationValue)
            .offset(y = -initialOffsetAnimationValue),
        text = stringResource(id = R.string.gameplay_screen_instructions, totalQueensToPlace),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun WinningText(
    modifier: Modifier = Modifier,
    isWin: Boolean,
    allSolutionsCount: Int,
) {
    Text(
        modifier = modifier
            .padding(horizontal = Spacing.L)
            .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy))
            .fillMaxWidth(if (isWin) 1f else 0f),
        text = stringResource(id = R.string.gameplay_screen_winning_message, allSolutionsCount),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge.copy(lineBreak = LineBreak.Heading, color = MaterialTheme.colorScheme.tertiary),
    )
}

@Composable
private fun QueensLeftText(
    modifier: Modifier = Modifier,
    queensLeft: Int,
    initialAlphaAnimationValue: Float,
    initialOffsetAnimationValue: Dp,
) {
    Text(
        modifier = modifier
            .padding(all = Spacing.xxL)
            .alpha(alpha = initialAlphaAnimationValue)
            .offset(y = initialOffsetAnimationValue),
        text = stringResource(id = R.string.gameplay_screen_queens_left, queensLeft)
    )
}

@Composable
private fun ControlButtons(
    modifier: Modifier = Modifier,
    isRestartGameButtonEnabled: Boolean,
    initialAlphaAnimationValue: Float,
    initialOffsetAnimationValue: Dp,
    onResetClick: () -> Unit,
    onPickDifferentBoardClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(top = Spacing.xL)
            .alpha(alpha = initialAlphaAnimationValue)
            .offset(y = initialOffsetAnimationValue),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Button(
            modifier = Modifier.padding(end = Spacing.xS),
            enabled = isRestartGameButtonEnabled,
            onClick = onResetClick,
        ) {
            Text(text = stringResource(id = R.string.gameplay_screen_restart_game))
        }
        Button(
            modifier = Modifier.padding(start = Spacing.xS),
            onClick = onPickDifferentBoardClick,
        ) {
            Text(text = stringResource(id = R.string.gameplay_screen_pick_different_board))
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
    val queenSizeAnimation = animateFloatAsState(targetValue = if (tile.showQueen) 1f else 0f)
    var initialAnimationValue by remember { mutableFloatStateOf(0f) }
    val initialAnimation = animateFloatAsState(targetValue = initialAnimationValue)

    LaunchedEffect(key1 = Unit) {
        // delay the animation of tiles appearing such that it creates a gradually appearing effect
        delay(50L * Random.nextInt(0, 10))
        initialAnimationValue = 1f
    }

    Box(
        modifier = modifier
            .fillMaxSize(fraction = initialAnimation.value)
            .alpha(alpha = 0.01f + initialAnimation.value)
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
        AnimatedVisibility(
            visible = tile.isError,
            enter = fadeIn(animationSpec = tween(durationMillis = ERROR_ANIMATION_LENGTH)),
            exit = fadeOut(animationSpec = tween(durationMillis = ERROR_ANIMATION_LENGTH))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.7f)
                    .background(MaterialTheme.colorScheme.tertiary)
            )
        }
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
private fun CelebrationOverlay() {
    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = (0..5).map { partySetup },
    )
}

private val partySetup = Party(
    speed = 0f,
    maxSpeed = 30f,
    damping = 0.9f,
    spread = 360,
    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
    position = Position.Relative(0.5, 0.3),
    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
)

@Composable
private fun UiEventObserver(
    uiEventFlow: Flow<GamePlayViewModel.UiEvent>,
    onNavigateBack: () -> Unit,
    onRestartGame: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        uiEventFlow.collect { uiEvent ->
            when (uiEvent) {
                is GamePlayViewModel.UiEvent.NavigateBack -> onNavigateBack()
                is GamePlayViewModel.UiEvent.RestartGame -> onRestartGame()
            }
        }
    }
}

private data class ContentDataHolder(
    val uiState: GamePlayViewModel.UiState,
    val initialAlphaAnimationValue: Float,
    val initialOffsetAnimationValue: Dp,
    val onTileTap: (Coordinates) -> Unit,
    val onResetClick: () -> Unit,
    val onPickDifferentBoardClick: () -> Unit,
)
