package com.roman.bookapp.navigation.animations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

object NavigationAnimation {

    private const val ANIMATION_TIME_MILLIS = 300
    private const val DELAY_TIME_MILLIS = 90
    private const val MAX_SCALE = 1.2f
    private const val MIN_SCALE = 0.8f

    fun scaleIntoContainer(
        direction: ContainerAnimationDirection = ContainerAnimationDirection.Inwards
    ) = scaleIn(
        animationSpec = tween(
            durationMillis = ANIMATION_TIME_MILLIS,
            delayMillis = DELAY_TIME_MILLIS
        ),
        initialScale = when (direction) {
            ContainerAnimationDirection.Inwards -> MAX_SCALE
            ContainerAnimationDirection.Outwards -> MIN_SCALE
        }
    )

    fun scaleOutOfContainer(
        direction: ContainerAnimationDirection = ContainerAnimationDirection.Outwards
    ) = scaleOut(
        animationSpec = tween(
            durationMillis = ANIMATION_TIME_MILLIS,
            delayMillis = DELAY_TIME_MILLIS
        ),
        targetScale = when (direction) {
            ContainerAnimationDirection.Inwards -> MIN_SCALE
            ContainerAnimationDirection.Outwards -> MAX_SCALE
        }
    )

    fun fadeIntoContainer() = fadeIn(
        tween(
            durationMillis = ANIMATION_TIME_MILLIS,
            delayMillis = DELAY_TIME_MILLIS
        )
    )

    fun fadeOutOfContainer() = fadeOut(
        tween(
            durationMillis = ANIMATION_TIME_MILLIS,
            delayMillis = DELAY_TIME_MILLIS
        )
    )

    fun slideInFrom(
        direction: HorizontalAnimationDirection = HorizontalAnimationDirection.Start
    ) = slideInHorizontally(
        animationSpec = tween(durationMillis = ANIMATION_TIME_MILLIS),
        initialOffsetX = { fullWidth ->
            when (direction) {
                HorizontalAnimationDirection.Start -> -fullWidth
                HorizontalAnimationDirection.End -> fullWidth
            }
        }
    )

    fun slideOutTo(
        direction: HorizontalAnimationDirection = HorizontalAnimationDirection.Start
    ) = slideOutHorizontally(
        animationSpec = tween(durationMillis = ANIMATION_TIME_MILLIS),
        targetOffsetX = { fullWidth ->
            when (direction) {
                HorizontalAnimationDirection.Start -> -fullWidth
                HorizontalAnimationDirection.End -> fullWidth
            }
        }
    )

    fun slideInFrom(
        direction: VerticalAnimationDirection = VerticalAnimationDirection.Top
    ) = slideInVertically(
        animationSpec = tween(durationMillis = ANIMATION_TIME_MILLIS),
        initialOffsetY = { fullHeight ->
            when (direction) {
                VerticalAnimationDirection.Top -> -fullHeight
                VerticalAnimationDirection.Bottom -> fullHeight
            }
        }
    )

    fun slideOutTo(
        direction: VerticalAnimationDirection = VerticalAnimationDirection.Top
    ) = slideOutVertically(
        animationSpec = tween(durationMillis = ANIMATION_TIME_MILLIS),
        targetOffsetY = { fullHeight ->
            when (direction) {
                VerticalAnimationDirection.Top -> -fullHeight
                VerticalAnimationDirection.Bottom -> fullHeight
            }
        }
    )
}