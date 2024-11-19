package com.roman.bookapp.book.domain

data class Book(
    val id: String,
    val title: String,
    val imageUrl: String,
    val author: List<String>,
    val description: String?,
    val language: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val numPages: Int?,
    val numEditions: Int,
)