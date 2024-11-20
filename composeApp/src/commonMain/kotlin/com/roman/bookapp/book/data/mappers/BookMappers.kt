package com.roman.bookapp.book.data.mappers

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
        author = authorNames ?: emptyList(),
        description = null,
        language = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingsCount = ratingsCount,
        numPages = numberOfPagesMedian,
        numEditions = numberOfEditions ?: 1,
    )
}