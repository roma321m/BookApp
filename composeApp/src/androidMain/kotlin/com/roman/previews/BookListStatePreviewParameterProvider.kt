package com.roman.previews

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.roman.bookapp.book.domain.Book
import com.roman.bookapp.book.presentation.book_list.BookListState

class BookListStatePreviewParameterProvider : PreviewParameterProvider<BookListState> {

    private val book1 = Book(
        id = "1",
        title = "Kotlin for Android",
        imageUrl = "",
        author = listOf("Roman", "Ivan"),
        description = "Kotlin for Android description",
        language = listOf("English"),
        firstPublishYear = null,
        averageRating = 4.58456,
        ratingsCount = 1000,
        numEditions = 2,
        numPages = 500
    )

    private val book2 = Book(
        id = "2",
        title = "Kotlin Advanced",
        imageUrl = "",
        author = listOf("Ivan"),
        description = null,
        language = listOf("English"),
        firstPublishYear = null,
        averageRating = 3.156,
        ratingsCount = 1000,
        numEditions = 2,
        numPages = 500
    )

    private val books = (1..100).map {
        book1.copy(
            id = it.toString()
        )
    }

    private val emptyUiState = BookListState()
    private val loadingUiState = BookListState(
        isLoading = true
    )
    private val searchUiState = BookListState(
        searchQuery = "Kotlin",
        searchResult = listOf(book1, book2)
    )
    private val longListUiState = BookListState(
        searchQuery = "Something",
        searchResult = books,
        favoriteBooks = books
    )

    override val values: Sequence<BookListState> = sequenceOf(
        emptyUiState,
        loadingUiState,
        searchUiState,
        longListUiState
    )
}