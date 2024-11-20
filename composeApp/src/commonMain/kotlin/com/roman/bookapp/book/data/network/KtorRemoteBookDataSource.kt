package com.roman.bookapp.book.data.network

import com.roman.bookapp.book.data.dto.SearchResponseDto
import com.roman.bookapp.core.data.safeCall
import com.roman.bookapp.core.domain.DataError
import com.roman.bookapp.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultsLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultsLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,cover_i,author_key,author_name,cover_edition_key,first_publish_year,ratings_average,ratings_count,language,number_of_pages_median,edition_count"
                )
            }
        }
    }

}