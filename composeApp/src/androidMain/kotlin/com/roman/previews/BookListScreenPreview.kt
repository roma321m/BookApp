package com.roman.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.roman.bookapp.book.presentation.book_list.BookListScreenView
import com.roman.bookapp.book.presentation.book_list.BookListState


@Preview
@Composable
private fun BookListScreenPreview(
    @PreviewParameter(BookListStatePreviewParameterProvider::class) uiState: BookListState,
) {
    MaterialTheme {
        BookListScreenView(
            uiState = uiState,
            onAction = {},
        )
    }
}