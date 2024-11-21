package com.roman.bookapp.book.data.repository

import androidx.sqlite.SQLiteException
import com.roman.bookapp.book.data.database.FavoriteBookDao
import com.roman.bookapp.book.data.mappers.toBook
import com.roman.bookapp.book.data.mappers.toBookEntity
import com.roman.bookapp.book.data.network.RemoteBookDataSource
import com.roman.bookapp.book.domain.Book
import com.roman.bookapp.book.domain.IBookRepository
import com.roman.bookapp.core.domain.DataError
import com.roman.bookapp.core.domain.EmptyResult
import com.roman.bookapp.core.domain.Result
import com.roman.bookapp.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
): IBookRepository {

    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { responseDto ->
                responseDto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        return if (localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { entities ->
                entities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { entities ->
                entities.any {
                    it.id == id
                }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsertBook(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.NO_STORAGE_SPACE)
        }
    }

    override suspend fun deleteFromFavorite(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }


}