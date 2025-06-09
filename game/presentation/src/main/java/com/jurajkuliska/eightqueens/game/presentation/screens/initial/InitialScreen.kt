package com.jurajkuliska.eightqueens.game.presentation.screens.initial

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jurajkuliska.eightqueens.game.presentation.R
import com.jurajkuliska.eightqueens.game.presentation.model.BoardSize
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import com.jurajkuliska.eightqueens.ui.components.EightQueensScaffold
import com.jurajkuliska.eightqueens.ui.components.OrientationAwareContainer
import com.jurajkuliska.eightqueens.ui.theme.Spacing
import com.jurajkuliska.eightqueens.ui.theme.Typography
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel
import com.jurajkuliska.eightqueens.ui.R as UiR

@Composable
internal fun InitialScreen(
    onNext: (GameRoute.GamePlay) -> Unit,
) {
    val viewModel = koinViewModel<InitialViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    UiEventObserver(
        uiEventFlow = viewModel.uiEvent,
        onNext = onNext,
    )

    EightQueensScaffold {
        val enterAnimation = getEnterAnimation()

        val contentDataHolder = ContentDataHolder(
            enterAnimation = enterAnimation,
            boardSizePickerState = uiState.boardSizePickerState,
            onNext = viewModel::onNext,
            onBoardSizePickerOpen = viewModel::onBoardSizePickerOpen,
            onBoardSizePick = viewModel::onBoardSizePick,
            onBoardSizePickerDismiss = viewModel::onBoardSizePickerDismiss,
        )

        OrientationAwareContainer(
            modifier = Modifier.align(Alignment.Center),
            portrait = { contentDataHolder.PortraitContent() },
            landscape = { contentDataHolder.LandscapeContent() },
        )
    }
}

@Composable
private fun ContentDataHolder.LandscapeContent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Logo(
            modifier = Modifier
                .scale(scale = enterAnimation.logoScaleValue)
                .offset(x = enterAnimation.logoOffsetValue)
        )
        ChooseDifficulty(
            modifier = Modifier
                .padding(top = 60.dp)
                .alpha(alpha = enterAnimation.chooseDifficultyAlpha),
            pickerState = boardSizePickerState,
            onNext = onNext,
            onBoardSizePickerOpen = onBoardSizePickerOpen,
            onBoardSizePick = onBoardSizePick,
            onBoardSizePickerDismiss = onBoardSizePickerDismiss,
        )
    }
}

@Composable
private fun ContentDataHolder.PortraitContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Logo(
            modifier = Modifier
                .scale(scale = enterAnimation.logoScaleValue)
                .offset(y = enterAnimation.logoOffsetValue)
        )
        ChooseDifficulty(
            modifier = Modifier
                .padding(top = 60.dp)
                .alpha(alpha = enterAnimation.chooseDifficultyAlpha)
                .align(Alignment.CenterHorizontally),
            pickerState = boardSizePickerState,
            onNext = onNext,
            onBoardSizePickerOpen = onBoardSizePickerOpen,
            onBoardSizePick = onBoardSizePick,
            onBoardSizePickerDismiss = onBoardSizePickerDismiss,
        )
    }
}

@Composable
private fun Logo(
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
            modifier = Modifier.padding(all = Spacing.xL),
            text = stringResource(R.string.initial_screen_title),
            style = Typography.headlineLarge.copy(lineBreak = LineBreak.Heading),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ChooseDifficulty(
    pickerState: InitialViewModel.UiState.BoardSizePickerState,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onBoardSizePickerOpen: () -> Unit,
    onBoardSizePick: (BoardSize) -> Unit,
    onBoardSizePickerDismiss: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = Spacing.L)
    ) {

        Text(
            text = stringResource(id = R.string.initial_screen_choose_board_size_title),
            style = MaterialTheme.typography.headlineSmall,
        )

        BoardSizePickerDropDown(
            pickerState = pickerState,
            onBoardSizePickerOpen = onBoardSizePickerOpen,
            onBoardSizePick = onBoardSizePick,
            onBoardSizePickerDismiss = onBoardSizePickerDismiss,
        )

        Button(
            onClick = onNext,
        ) {
            Text(text = stringResource(id = R.string.initial_screen_play_button_title))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BoardSizePickerDropDown(
    modifier: Modifier = Modifier,
    pickerState: InitialViewModel.UiState.BoardSizePickerState,
    onBoardSizePickerOpen: () -> Unit,
    onBoardSizePick: (BoardSize) -> Unit,
    onBoardSizePickerDismiss: () -> Unit,
) {
    ExposedDropdownMenuBox(
        modifier = modifier
            .width(120.dp)
            .clip(MaterialTheme.shapes.medium),
        expanded = pickerState.isExpanded,
        onExpandedChange = {
            if (it) {
                onBoardSizePickerOpen()
            } else {
                onBoardSizePickerDismiss()
            }
        }
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryEditable),
                value = stringResource(pickerState.selectedOption.title),
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = pickerState.isExpanded)
                },
            )
        }

        ExposedDropdownMenu(
            expanded = pickerState.isExpanded,
            onDismissRequest = {
                onBoardSizePickerDismiss()
            }
        ) {
            pickerState.options.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = it.title))
                    },
                    onClick = {
                        onBoardSizePick(it)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
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
    onNext: (GameRoute.GamePlay) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        uiEventFlow.collect { uiEvent ->
            when (uiEvent) {
                is InitialViewModel.UiEvent.NavigateToGamePlay -> onNext(uiEvent.route)
            }
        }
    }
}

private data class EnterAnimation(
    val logoScaleValue: Float,
    val logoOffsetValue: Dp,
    val chooseDifficultyAlpha: Float,
)

private data class ContentDataHolder(
    val enterAnimation: EnterAnimation,
    val boardSizePickerState: InitialViewModel.UiState.BoardSizePickerState,
    val onNext: () -> Unit,
    val onBoardSizePickerOpen: () -> Unit,
    val onBoardSizePick: (BoardSize) -> Unit,
    val onBoardSizePickerDismiss: () -> Unit,
)
