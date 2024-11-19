package com.roman.bookapp

import androidx.compose.runtime.*
import com.roman.bookapp.book.presentation.book_list.BookListScreenRoot
import com.roman.bookapp.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    BookListScreenRoot(
        viewModel = remember { BookListViewModel() },
        onBookClick = {},
    )
}