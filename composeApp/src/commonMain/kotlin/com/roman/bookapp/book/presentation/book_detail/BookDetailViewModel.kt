package com.roman.bookapp.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.roman.bookapp.book.domain.Book
import com.roman.bookapp.book.domain.IBookRepository
import com.roman.bookapp.core.domain.onError
import com.roman.bookapp.core.domain.onSuccess
import com.roman.bookapp.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BookDetailViewModel(
    private val bookRepository: IBookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TAG = "BookDetailViewModel"
    }

    private val bookId = savedStateHandle.toRoute<Route.BookDetails>().id

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            fetchBookDetails()
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            _state.value
        )

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

    private fun onFavoriteClick() = viewModelScope.launch {
        println("$TAG onFavoriteClick")

        if (state.value.isFavorite) {
            bookRepository.deleteFromFavorite(bookId)
        } else {
            state.value.book?.let {
                bookRepository.markAsFavorite(it)
            }
        }
    }

    private fun onBackClick() {
        println("$TAG onBackClick")
    }

    private fun observeFavoriteStatus() {
        println("$TAG observeFavoriteState")
        bookRepository
            .isBookFavorite(bookId)
            .onEach { isFavorite ->
                println("$TAG observeFavoriteState isFavorite: $isFavorite")
                _state.update {
                    it.copy(isFavorite = isFavorite)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDetails() = viewModelScope.launch {
        println("$TAG fetchBookDetails")
        bookRepository
            .getBookDescription(bookId)
            .onSuccess { description ->
                _state.update { state ->
                    state.copy(
                        book = state.book?.copy(
                            description = description
                        ),
                        isLoading = false
                    )
                }
            }
            .onError {
                println("$TAG fetchBookDetails onError: $it")
            }
    }

}