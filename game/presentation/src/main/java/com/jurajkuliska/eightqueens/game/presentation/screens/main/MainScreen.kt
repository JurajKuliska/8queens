package com.jurajkuliska.eightqueens.game.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun MainScreen(
    onNext: () -> Unit,
) {
    Column {
        Text(text = "Game started")
        Button(
            onClick = onNext,
        ) {
            Text(text = "Click to Win!")
        }
    }
}
