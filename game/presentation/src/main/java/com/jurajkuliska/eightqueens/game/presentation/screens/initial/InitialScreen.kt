package com.jurajkuliska.eightqueens.game.presentation.screens.initial

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jurajkuliska.eightqueens.game.presentation.R
import com.jurajkuliska.eightqueens.game.presentation.model.BoardSize
import com.jurajkuliska.eightqueens.ui.components.EightQueensScaffold
import com.jurajkuliska.eightqueens.ui.theme.Typography
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel
import com.jurajkuliska.eightqueens.ui.R as UiR

@Composable
internal fun InitialScreen(
    onNext: () -> Unit,
) {
    val viewModel = koinViewModel<InitialViewModel>()

    UiEventObserver(
        uiEventFlow = viewModel.uiEvent,
        onNext = onNext,
    )

    EightQueensScaffold {
        val enterAnimation = getEnterAnimation()
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Logo(
                modifier = Modifier
                    .scale(scale = enterAnimation.logoScaleValue)
                    .offset(y = enterAnimation.logoOffsetValue)
            )
            ChooseDifficulty(
                modifier = Modifier
                    .padding(top = 120.dp)
                    .alpha(alpha = enterAnimation.chooseDifficultyAlpha),
                onNext = onNext,
                onBoardSizePicked = viewModel::onBoardSizePicked,
            )
        }
    }
}

@Composable
private fun ColumnScope.Logo(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = UiR.drawable.chess_queen),
            contentDescription = stringResource(id = R.string.initial_screen_game_icon_content_description),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
        )
        Text(
            modifier = Modifier.padding(20.dp),
            text = stringResource(R.string.initial_screen_title),
            style = Typography.headlineLarge.copy(lineBreak = LineBreak.Heading),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ColumnScope.ChooseDifficulty(
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onBoardSizePicked: (BoardSize) -> Unit,
) {
    Column(modifier = modifier.align(Alignment.CenterHorizontally)) {
        Row {
            Text(
                text = stringResource(id = R.string.initial_screen_choose_board_size_title)
            )
        }
        Button(
            onClick = onNext,
        ) {
            Text(text = "Click to Play!")
        }
    }
}

@Composable
private fun getEnterAnimation(): EnterAnimation {
    var logoScaleValue by remember { mutableFloatStateOf(0.0f) }
    var logoOffsetValue by remember { mutableStateOf(120.dp) }
    var chooseDifficultyValue by remember { mutableFloatStateOf(0.01f) }
    val logoScaleAnimation = animateFloatAsState(
        targetValue = logoScaleValue,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        finishedListener = {
            logoOffsetValue = 0.dp
        }
    )
    val logoOffsetAnimation = animateDpAsState(
        targetValue = logoOffsetValue,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        finishedListener = {
            chooseDifficultyValue = 1f
        }
    )
    val chooseDifficultyAlphaAnimation = animateFloatAsState(
        targetValue = chooseDifficultyValue,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
    )
    LaunchedEffect(key1 = Unit) {
        logoScaleValue = 1f
    }
    return EnterAnimation(
        logoScaleValue = logoScaleAnimation.value,
        logoOffsetValue = logoOffsetAnimation.value,
        chooseDifficultyAlpha = chooseDifficultyAlphaAnimation.value,
    )
}

@Composable
private fun UiEventObserver(
    uiEventFlow: Flow<InitialViewModel.UiEvent>,
    onNext: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        uiEventFlow.collect { uiEvent ->
            when (uiEvent) {
                InitialViewModel.UiEvent.Next -> onNext()
            }
        }
    }
}

private data class EnterAnimation(
    val logoScaleValue: Float,
    val logoOffsetValue: Dp,
    val chooseDifficultyAlpha: Float,
)
