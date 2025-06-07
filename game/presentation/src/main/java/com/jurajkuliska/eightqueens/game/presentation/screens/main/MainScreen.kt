package com.jurajkuliska.eightqueens.game.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jurajkuliska.eightqueens.ui.components.EightQueensScaffold

@Composable
internal fun MainScreen(
    onNext: () -> Unit,
) {
    EightQueensScaffold {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Game started")
            Button(
                onClick = onNext,
            ) {
                Text(text = "Click to Win!")
            }
        }
    }
}
