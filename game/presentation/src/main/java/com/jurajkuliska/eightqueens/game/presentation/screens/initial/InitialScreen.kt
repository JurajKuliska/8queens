package com.jurajkuliska.eightqueens.game.presentation.screens.initial

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun InitialScreen(
    onNext: () -> Unit,
) {
    Column {
        Text(text = "Choose difficulty")
        Button(
            onClick = onNext,
        ) {
            Text(text = "Click to Play!")
        }
    }
}
