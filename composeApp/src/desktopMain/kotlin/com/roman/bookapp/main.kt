package com.roman.bookapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.roman.bookapp.di.initKoin


fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "BookApp",
        ) {
            App()
        }
    }
}