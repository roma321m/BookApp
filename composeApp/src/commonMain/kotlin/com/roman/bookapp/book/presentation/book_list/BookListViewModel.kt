package com.roman.bookapp.book.presentation.book_list

import androidx.lifecycle.ViewModel
import com.roman.bookapp.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel : ViewModel() {

    companion object {
        const val TAG = "BookListViewModel"
    }

    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

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
}