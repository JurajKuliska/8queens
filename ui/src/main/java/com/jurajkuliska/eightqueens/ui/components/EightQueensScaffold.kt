package com.jurajkuliska.eightqueens.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EightQueensScaffold(
    topBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    Scaffold(
        topBar = topBar,
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            content()
        }
    }
}