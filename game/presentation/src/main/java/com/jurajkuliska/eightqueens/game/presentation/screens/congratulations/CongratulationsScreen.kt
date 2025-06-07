package com.jurajkuliska.eightqueens.game.presentation.screens.congratulations

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute

@Composable
internal fun CongratulationsScreen(
    navArgs: GameRoute.Congratulations,
    onBack: () -> Unit,
    onFinish: () -> Unit,
) {
    Column {
        Button(onClick = onBack) {
            Text("Back")
        }
        Text(text = navArgs.message)
        Button(
            onClick = onFinish
        ) {
            Text(text = "Done")
        }
    }
}
