package com.roman.bookapp.core.domain

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET_CONNECTION,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError {
        NO_STORAGE_ACCESS,
        NO_STORAGE_SPACE,
        UNKNOWN
    }
}