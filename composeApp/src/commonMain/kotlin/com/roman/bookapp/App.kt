package com.roman.bookapp

import androidx.compose.runtime.Composable
import com.roman.bookapp.book.presentation.book_list.BookListScreenRoot
import com.roman.bookapp.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<BookListViewModel>()
    BookListScreenRoot(
        viewModel = viewModel,
        onBookClick = {},
    )
}