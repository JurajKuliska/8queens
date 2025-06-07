package com.jurajkuliska.eightqueens.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jurajkuliska.eightqueens.ui.theme.EightQueensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EightQueensTheme {
                MainNavGraph()
            }
        }
    }
}