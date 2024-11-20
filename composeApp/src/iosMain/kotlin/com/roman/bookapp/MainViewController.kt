package com.roman.bookapp

import androidx.compose.ui.window.ComposeUIViewController
import com.roman.bookapp.di.initKoin


fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}