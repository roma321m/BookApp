package com.roman.bookapp.book.presentation.book_list

import com.roman.bookapp.book.domain.Book
import com.roman.bookapp.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResult: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessages: UiText? = null
)