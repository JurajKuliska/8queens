package com.jurajkuliska.eightqueens.game.presentation.screens.congratulations

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jurajkuliska.eightqueens.game.presentation.navigation.GameRoute
import com.jurajkuliska.eightqueens.ui.components.EightQueensScaffold

@Composable
internal fun CongratulationsScreen(
    navArgs: GameRoute.Congratulations,
    onBack: () -> Unit,
    onFinish: () -> Unit,
) {
    EightQueensScaffold {
        Column(modifier = Modifier.align(Alignment.Center)) {
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
}
