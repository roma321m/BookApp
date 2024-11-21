package com.roman.bookapp.navigation.routes

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.roman.bookapp.book.presentation.SelectedBookViewModel
import com.roman.bookapp.book.presentation.book_list.BookListAction
import com.roman.bookapp.book.presentation.book_list.BookListScreenView
import com.roman.bookapp.book.presentation.book_list.BookListViewModel
import com.roman.bookapp.navigation.Route
import com.roman.bookapp.navigation.util.sharedKoinViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.bookListComposable(
    navController: NavController
) {
    composable<Route.BookList>(
        exitTransition = { slideOutHorizontally() },
        popEnterTransition = { slideInHorizontally() }
    ) { entry ->
        val selectedBookViewModel = entry.sharedKoinViewModel<SelectedBookViewModel>(navController)
        val viewModel = koinViewModel<BookListViewModel>()
        val uiState = viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            selectedBookViewModel.onSelectBook(null)
        }

        BookListScreenView(
            uiState = uiState.value,
            onAction = {
                when (it) {
                    is BookListAction.OnBookClick -> {
                        selectedBookViewModel.onSelectBook(it.book)
                        navController.navigate(
                            route = Route.BookDetails(it.book.id)
                        )
                    }
                    else -> Unit
                }
                viewModel.onAction(it)
            }
        )
    }
}