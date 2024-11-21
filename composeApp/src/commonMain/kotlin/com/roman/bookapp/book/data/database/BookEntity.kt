package com.roman.bookapp.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_entity")
data class BookEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String,
    val languages: List<String>,
    val authors: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val numberOfPagesMedian: Int?,
    val numberOfEdition: Int
)