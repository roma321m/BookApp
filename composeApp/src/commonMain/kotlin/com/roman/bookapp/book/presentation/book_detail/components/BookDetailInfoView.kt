package com.roman.bookapp.book.presentation.book_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.description_not_available
import bookapp.composeapp.generated.resources.languages_title
import bookapp.composeapp.generated.resources.pages_title
import bookapp.composeapp.generated.resources.rating_title
import bookapp.composeapp.generated.resources.synopsis_title
import com.roman.bookapp.book.domain.Book
import com.roman.bookapp.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.BookDetailInfoView(
    isLoading: Boolean,
    book: Book,
) {
    Text(
        text = book.title,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
    Text(
        text = book.authors.joinToString(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )

    FlowRow(
        modifier = Modifier
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        book.averageRating?.let { rating ->
            TitledContent(
                title = stringResource(Res.string.rating_title),
            ) {
                BookChipView {
                    Row {
                        Text(
                            text = "${round(rating * 10) / 10.0}"
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = SandYellow
                        )
                    }
                }
            }
        }

        book.numPages?.let { pages ->
            TitledContent(
                title = stringResource(Res.string.pages_title)
            ) {
                BookChipView {
                    Text(
                        text = "$pages"
                    )
                }
            }
        }

        if (book.languages.isNotEmpty()) {
            TitledContent(
                title = stringResource(Res.string.languages_title)
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    book.languages.forEach { language ->
                        BookChipView {
                            Text(
                                text = language,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }

    Text(
        modifier = Modifier
            .align(Alignment.Start)
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                bottom = 8.dp
            ),
        text = stringResource(Res.string.synopsis_title),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Start
    )

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp),
            text = if (book.description.isNullOrBlank()) {
                stringResource(Res.string.description_not_available)
            } else {
                book.description
            },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            color = Color.Black
        )
    }
}