package com.roman.bookapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.roman.bookapp.navigation.routes.bookDetailComposable
import com.roman.bookapp.navigation.routes.bookListComposable

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Route.BookGraph
    ) {
        navigation<Route.BookGraph>(
            startDestination = Route.BookList
        ) {
            bookListComposable(navController)
            bookDetailComposable(navController)
        }
    }
}