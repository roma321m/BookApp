package com.roman.bookapp.core.presentation

import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.error_no_internet_connection
import bookapp.composeapp.generated.resources.error_no_storage_access
import bookapp.composeapp.generated.resources.error_no_storage_space
import bookapp.composeapp.generated.resources.error_other
import bookapp.composeapp.generated.resources.error_request_timeout
import bookapp.composeapp.generated.resources.too_many_requests
import com.roman.bookapp.core.domain.DataError

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        DataError.Local.NO_STORAGE_ACCESS -> Res.string.error_no_storage_access
        DataError.Local.NO_STORAGE_SPACE -> Res.string.error_no_storage_space
        DataError.Local.UNKNOWN -> Res.string.error_other

        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.too_many_requests
        DataError.Remote.NO_INTERNET_CONNECTION -> Res.string.error_no_internet_connection
        DataError.Remote.SERVER_ERROR,
        DataError.Remote.SERIALIZATION,
        DataError.Remote.UNKNOWN -> Res.string.error_other
    }

    return UiText.StringResourceId(stringRes)
}