package com.roman.bookapp.navigation.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.roman.bookapp.book.presentation.SelectedBookViewModel
import com.roman.bookapp.book.presentation.book_detail.BookDetailAction
import com.roman.bookapp.book.presentation.book_detail.BookDetailScreenView
import com.roman.bookapp.book.presentation.book_detail.BookDetailViewModel
import com.roman.bookapp.navigation.Route
import com.roman.bookapp.navigation.animations.HorizontalAnimationDirection
import com.roman.bookapp.navigation.animations.NavigationAnimation
import com.roman.bookapp.navigation.util.sharedKoinViewModel
import org.koin.compose.viewmodel.koinViewModel


fun NavGraphBuilder.bookDetailComposable(
    navController: NavController
) {
    composable<Route.BookDetails>(
        enterTransition = {
            NavigationAnimation.slideInFrom(HorizontalAnimationDirection.End)
        }
    ) { entry ->
        val selectedBookViewModel = entry.sharedKoinViewModel<SelectedBookViewModel>(navController)
        val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

        val viewModel = koinViewModel<BookDetailViewModel>()
        val uiState = viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(selectedBook) {
            selectedBook?.let {
                viewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
            }
        }

        BookDetailScreenView(
            state = uiState.value,
            onAction = { action ->
                when (action) {
                    BookDetailAction.OnBackClick -> navController.navigateUp()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}