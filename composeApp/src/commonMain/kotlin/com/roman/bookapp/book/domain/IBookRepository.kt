package com.roman.bookapp.book.domain

import com.roman.bookapp.core.domain.DataError
import com.roman.bookapp.core.domain.Result

interface IBookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>
}