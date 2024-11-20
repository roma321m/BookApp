package com.roman.bookapp.book.data.repository

import com.roman.bookapp.book.data.mappers.toBook
import com.roman.bookapp.book.data.network.RemoteBookDataSource
import com.roman.bookapp.book.domain.Book
import com.roman.bookapp.book.domain.IBookRepository
import com.roman.bookapp.core.domain.DataError
import com.roman.bookapp.core.domain.Result
import com.roman.bookapp.core.domain.map

class BookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
): IBookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { responseDto ->
                responseDto.results.map { it.toBook() }
            }
    }
}