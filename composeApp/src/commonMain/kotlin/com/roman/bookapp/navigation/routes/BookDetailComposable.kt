package com.roman.bookapp.navigation.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.roman.bookapp.book.presentation.SelectedBookViewModel
import com.roman.bookapp.navigation.Route
import com.roman.bookapp.navigation.animations.HorizontalAnimationDirection
import com.roman.bookapp.navigation.animations.NavigationAnimation
import com.roman.bookapp.navigation.util.sharedKoinViewModel


fun NavGraphBuilder.bookDetailComposable(
    navController: NavController
) {
    composable<Route.BookDetails>(
        enterTransition = {
            NavigationAnimation.slideInFrom(HorizontalAnimationDirection.End)
        }
    ) { entry ->
        // TODO temp UI: implement screen with book details
        val selectedBookViewModel = entry.sharedKoinViewModel<SelectedBookViewModel>(navController)
        val arguments = entry.toRoute<Route.BookDetails>()
        val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "book id: ${arguments.id}" +
                        "book: $selectedBook",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}