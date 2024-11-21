package com.roman.bookapp.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.back_button
import bookapp.composeapp.generated.resources.book_cover_title
import bookapp.composeapp.generated.resources.book_error_2
import bookapp.composeapp.generated.resources.mark_as_favorite
import bookapp.composeapp.generated.resources.remove_from_favorites
import coil3.compose.rememberAsyncImagePainter
import com.roman.bookapp.core.presentation.DarkBlue
import com.roman.bookapp.core.presentation.DesertWhite
import com.roman.bookapp.core.presentation.PulseAnimationView
import com.roman.bookapp.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    blurImageFraction: Float = 0.3f,
    content: @Composable () -> Unit
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image size"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(blurImageFraction)
                    .fillMaxWidth()
                    .background(DarkBlue)
            ) {
                if (imageLoadResult?.isSuccess == true) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(20.dp),
                        painter = painter,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1 - blurImageFraction)
                    .fillMaxWidth()
                    .background(DesertWhite)
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
                .statusBarsPadding(),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.back_button),
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(
                modifier = Modifier.fillMaxHeight(blurImageFraction / 2)
            )

            BookCoverCardView(
                imageLoadResult = imageLoadResult,
                painter = painter,
                onFavoriteClick = onFavoriteClick,
                height = blurImageFraction,
                isFavorite = isFavorite
            )

            content()
        }
    }
}

@Composable
private fun BookCoverCardView(
    imageLoadResult: Result<Painter>?,
    painter: Painter,
    onFavoriteClick: () -> Unit,
    height: Float,
    isFavorite: Boolean
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxHeight(height)
            .aspectRatio(2 / 3f),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 15.dp
        )
    ) {
        AnimatedContent(
            targetState = imageLoadResult
        ) { result ->
            when (result) {
                null -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    PulseAnimationView(
                        modifier = Modifier.size(60.dp)
                    )
                }

                else -> {
                    Box {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent),
                            painter = if (result.isSuccess) {
                                painter
                            } else {
                                painterResource(Res.drawable.book_error_2)
                            },
                            contentDescription = stringResource(Res.string.book_cover_title),
                            contentScale = if (result.isSuccess) {
                                ContentScale.Crop
                            } else {
                                ContentScale.Fit
                            }
                        )
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            SandYellow,
                                            Color.Transparent
                                        ),
                                        radius = 70f
                                    )
                                ),
                            onClick = onFavoriteClick
                        ) {
                            Icon(
                                tint = Color.Red,
                                imageVector = if (isFavorite) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Outlined.FavoriteBorder
                                },
                                contentDescription = if (isFavorite) {
                                    stringResource(Res.string.remove_from_favorites)
                                } else {
                                    stringResource(Res.string.mark_as_favorite)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}