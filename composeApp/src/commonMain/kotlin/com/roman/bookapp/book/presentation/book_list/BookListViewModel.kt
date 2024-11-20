@file:OptIn(FlowPreview::class)

package com.roman.bookapp.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.bookapp.book.domain.Book
import com.roman.bookapp.book.domain.IBookRepository
import com.roman.bookapp.core.domain.onError
import com.roman.bookapp.core.domain.onSuccess
import com.roman.bookapp.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BookListViewModel(
    private val bookRepository: IBookRepository
) : ViewModel() {

    companion object {
        const val TAG = "BookListViewModel"
    }

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            _state.value
        )

    private var cachedBooks: List<Book> = emptyList()
    private var searchJob: Job? = null

    fun onAction(action: BookListAction) {
        println("$TAG: $action")
        when (action) {
            is BookListAction.OnBookClick -> onBookClick(action.book)
            is BookListAction.OnSearchQueryChange -> onSearchQueryChange(action.query)
            is BookListAction.OnTabSelected -> onTabSelected(action.index)
        }
    }

    private fun onBookClick(book: Book) {
        println("$TAG: $book")
        // TODO handle book click
    }

    private fun onTabSelected(index: Int) {
        println("$TAG: $index")
        _state.update {
            it.copy(selectedTabIndex = index)
        }
    }

    private fun onSearchQueryChange(query: String) {
        println("$TAG: $query")
        _state.update {
            it.copy(searchQuery = query)
        }
    }

    private fun observeSearchQuery() {
        println("$TAG: observeSearchQuery")
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessages = null,
                                searchResult = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        println("$TAG: $query")
        _state.update {
            it.copy(isLoading = true)
        }

        bookRepository.searchBooks(query)
            .onSuccess { searchResult ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessages = null,
                        searchResult = searchResult,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResult = emptyList(),
                        isLoading = false,
                        errorMessages = error.toUiText()
                    )
                }
            }
    }
}