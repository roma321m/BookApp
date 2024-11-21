package com.roman.bookapp.book.data.network

import com.roman.bookapp.book.data.dto.BookWorkDto
import com.roman.bookapp.book.data.dto.SearchResponseDto
import com.roman.bookapp.core.domain.DataError
import com.roman.bookapp.core.domain.Result

interface RemoteBookDataSource {

    suspend fun searchBooks(
        query: String,
        resultsLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(
        bookWorkId: String
    ): Result<BookWorkDto, DataError.Remote>
}