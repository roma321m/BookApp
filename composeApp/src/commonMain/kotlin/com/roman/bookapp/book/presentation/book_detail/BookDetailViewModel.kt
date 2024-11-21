package com.roman.bookapp.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import com.roman.bookapp.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookDetailViewModel : ViewModel() {

    companion object {
        private const val TAG = "BookDetailViewModel"
    }

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnBackClick -> onBackClick()
            BookDetailAction.OnFavoriteClick -> onFavoriteClick()
            is BookDetailAction.OnSelectedBookChange -> onSelectedBookChange(action.book)
        }
    }

    private fun onSelectedBookChange(book: Book) {
        println("$TAG onSelectedBookChange: $book")

        _state.update {
            it.copy(
                book = book
            )
        }
    }

    private fun onFavoriteClick() {
        println("$TAG onFavoriteClick")
    }

    private fun onBackClick() {
        println("$TAG onBackClick")
    }

}