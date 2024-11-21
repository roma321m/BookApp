package com.roman.bookapp.book.domain

import com.roman.bookapp.core.domain.DataError
import com.roman.bookapp.core.domain.EmptyResult
import com.roman.bookapp.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>

    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorite(id: String)
}