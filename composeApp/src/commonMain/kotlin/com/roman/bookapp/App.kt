package com.roman.bookapp

import androidx.compose.runtime.*
import com.roman.bookapp.book.data.network.KtorRemoteBookDataSource
import com.roman.bookapp.book.data.repository.BookRepository
import com.roman.bookapp.book.presentation.book_list.BookListScreenRoot
import com.roman.bookapp.book.presentation.book_list.BookListViewModel
import com.roman.bookapp.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    engine: HttpClientEngine
) {
    BookListScreenRoot(
        // Fixme - temp initializations for tests
        viewModel = remember { BookListViewModel(
            bookRepository = BookRepository(
                remoteBookDataSource = KtorRemoteBookDataSource(
                    httpClient = HttpClientFactory.create(engine)
                )
            )
        ) },
        onBookClick = {},
    )
}