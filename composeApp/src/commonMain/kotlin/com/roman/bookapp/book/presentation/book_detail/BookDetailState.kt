package com.roman.bookapp.book.presentation.book_detail

import com.roman.bookapp.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null,
)