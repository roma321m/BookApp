package com.roman.bookapp.book.data.mappers

import com.roman.bookapp.book.data.database.BookEntity
import com.roman.bookapp.book.data.dto.SearchedBookDto
import com.roman.bookapp.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast('/'),
        title = title,
        imageUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingsCount = ratingsCount,
        numberOfPagesMedian = numberOfPagesMedian,
        numberOfEdition = numberOfEditions ?: 1,
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingsCount = ratingsCount,
        numberOfPagesMedian = numberOfPagesMedian,
        numberOfEdition = numberOfEdition
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        imageUrl = imageUrl,
        authors = authors,
        description = description,
        languages = languages,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingsCount = ratingsCount,
        numberOfPagesMedian = numberOfPagesMedian,
        numberOfEdition = numberOfEdition,
    )
}