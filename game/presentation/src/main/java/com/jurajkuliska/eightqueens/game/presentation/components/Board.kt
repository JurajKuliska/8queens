package com.jurajkuliska.eightqueens.game.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.jurajkuliska.eightqueens.game.domain.model.Coordinates
import com.jurajkuliska.eightqueens.game.presentation.R
import com.jurajkuliska.eightqueens.game.presentation.model.BoardStateUi
import com.jurajkuliska.eightqueens.game.presentation.model.BoardTileUi
import com.jurajkuliska.eightqueens.ui.theme.PieceColor
import com.jurajkuliska.eightqueens.ui.theme.Spacing
import com.jurajkuliska.eightqueens.ui.theme.TileDark
import com.jurajkuliska.eightqueens.ui.theme.TileLight
import kotlinx.coroutines.delay
import kotlin.random.Random
import com.jurajkuliska.eightqueens.ui.R as uiR

private const val ERROR_ANIMATION_LENGTH = 50

@Composable
internal fun Board(
    state: BoardStateUi,
    onTileTap: (Coordinates) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .aspectRatio(ratio = 1f)
            .background(TileLight)
    ) {
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