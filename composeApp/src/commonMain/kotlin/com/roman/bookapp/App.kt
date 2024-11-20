package com.roman.bookapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.roman.bookapp.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        AppNavigation(
            navController = navController
        )
    }
}