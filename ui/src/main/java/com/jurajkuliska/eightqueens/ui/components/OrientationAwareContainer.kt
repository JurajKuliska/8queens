package com.jurajkuliska.eightqueens.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun OrientationAwareContainer(
    modifier: Modifier = Modifier,
    portrait: @Composable BoxScope.() -> Unit,
    landscape: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier) {
        val configuration = LocalConfiguration.current
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> landscape()
            else -> portrait()
        }
    }
}