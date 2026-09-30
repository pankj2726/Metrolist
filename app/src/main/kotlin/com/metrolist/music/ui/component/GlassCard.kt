/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metrolist.music.constants.PlayerBackgroundStyle

/**
 * Premium Glassmorphism Card for Now Playing Screen
 * Creates a frosted glass effect with translucent background,
 * subtle border highlight, and soft shadow.
 */

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(28.dp),
    playerBackground: PlayerBackgroundStyle = PlayerBackgroundStyle.DEFAULT,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    elevation: Dp = 16.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    // Determine if we are on a dark blurred background (BLUR / GRADIENT always dark-ish)
    val isOnDarkBackground = remember(playerBackground, isDarkTheme) {
        when (playerBackground) {
            PlayerBackgroundStyle.BLUR, PlayerBackgroundStyle.GRADIENT, PlayerBackgroundStyle.GLASS -> true
            PlayerBackgroundStyle.DEFAULT -> isDarkTheme
        }
    }

    // Glass colors tuned for both light and dark
    val glassBrush = remember(isOnDarkBackground) {
        if (isOnDarkBackground) {
            // Dark background -> light frosted glass
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.18f),
                    Color.White.copy(alpha = 0.07f),
                    Color.White.copy(alpha = 0.12f)
                )
            )
        } else {
            // Light background -> soft white glass
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.85f),
                    Color.White.copy(alpha = 0.55f),
                    Color.White.copy(alpha = 0.70f)
                )
            )
        }
    }

    val borderBrush = remember(isOnDarkBackground) {
        if (isOnDarkBackground) {
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.35f),
                    Color.White.copy(alpha = 0.08f),
                    Color.White.copy(alpha = 0.15f)
                )
            )
        } else {
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.9f),
                    Color.Black.copy(alpha = 0.05f),
                    Color.White.copy(alpha = 0.5f)
                )
            )
        }
    }

    val shadowColor = if (isOnDarkBackground) Color.Black.copy(alpha = 0.35f) else Color.Black.copy(alpha = 0.15f)

    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                clip = false,
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .clip(shape)
            .background(glassBrush)
            .border(
                BorderStroke(1.dp, borderBrush),
                shape = shape
            )
    ) {
        // Inner highlight - top edge shine for realism
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isOnDarkBackground) 0.18f else 0.35f),
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black.copy(alpha = if (isOnDarkBackground) 0.08f else 0.04f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        content()
    }
}

/**
 * Glass Card specifically for Album Thumbnail
 * Adds extra inner padding and soft inner glow to make artwork pop
 */
@Composable
fun GlassThumbnailCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(32.dp),
    playerBackground: PlayerBackgroundStyle = PlayerBackgroundStyle.DEFAULT,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    val isOnDarkBackground = remember(playerBackground, isDarkTheme) {
        when (playerBackground) {
            PlayerBackgroundStyle.BLUR, PlayerBackgroundStyle.GRADIENT, PlayerBackgroundStyle.GLASS -> true
            PlayerBackgroundStyle.DEFAULT -> isDarkTheme
        }
    }

    // More pronounced glass for thumbnail container
    val glassBrush = remember(isOnDarkBackground) {
        if (isOnDarkBackground) {
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.20f),
                    Color.White.copy(alpha = 0.05f)
                )
            )
        } else {
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.75f),
                    Color.White.copy(alpha = 0.45f)
                )
            )
        }
    }

    val borderBrush = remember(isOnDarkBackground) {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = if (isOnDarkBackground) 0.40f else 0.85f),
                Color.White.copy(alpha = if (isOnDarkBackground) 0.10f else 0.30f)
            )
        )
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = 24.dp,
                shape = shape,
                clip = false,
                ambientColor = Color.Black.copy(alpha = 0.35f),
                spotColor = Color.Black.copy(alpha = 0.35f)
            )
            .clip(shape)
            .background(glassBrush)
            .border(BorderStroke(1.2.dp, borderBrush), shape)
            .padding(12.dp) // Inner padding creates the glass frame around artwork
    ) {
        // Inner container for artwork with its own clipping and subtle inner shadow
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    if (isOnDarkBackground) Color.Black.copy(alpha = 0.15f)
                    else Color.White.copy(alpha = 0.3f)
                )
        ) {
            content()
        }

        // Top highlight
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(shape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isOnDarkBackground) 0.22f else 0.45f),
                            Color.Transparent
                        ),
                        endY = 120f
                    )
                )
        )
    }
}

/**
 * Glass Card for Player Controls (title, slider, buttons)
 * Slightly more opaque for readability
 */
@Composable
fun GlassControlsCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(28.dp),
    playerBackground: PlayerBackgroundStyle = PlayerBackgroundStyle.DEFAULT,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable BoxScope.() -> Unit,
) {
    val isOnDarkBackground = remember(playerBackground, isDarkTheme) {
        when (playerBackground) {
            PlayerBackgroundStyle.BLUR, PlayerBackgroundStyle.GRADIENT, PlayerBackgroundStyle.GLASS -> true
            PlayerBackgroundStyle.DEFAULT -> isDarkTheme
        }
    }

    val glassBrush = remember(isOnDarkBackground) {
        if (isOnDarkBackground) {
            Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.16f),
                    Color.White.copy(alpha = 0.06f)
                )
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.82f),
                    Color.White.copy(alpha = 0.60f)
                )
            )
        }
    }

    val borderBrush = remember(isOnDarkBackground) {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = if (isOnDarkBackground) 0.30f else 0.8f),
                Color.White.copy(alpha = if (isOnDarkBackground) 0.08f else 0.25f)
            )
        )
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = 20.dp,
                shape = shape,
                clip = false,
                ambientColor = Color.Black.copy(alpha = if (isOnDarkBackground) 0.30f else 0.12f),
                spotColor = Color.Black.copy(alpha = if (isOnDarkBackground) 0.30f else 0.12f)
            )
            .clip(shape)
            .background(glassBrush)
            .border(BorderStroke(1.dp, borderBrush), shape)
    ) {
        // Subtle inner glow
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = if (isOnDarkBackground) 0.12f else 0.25f),
                            Color.Transparent,
                            Color.Transparent
                        )
                    )
                )
        )
        content()
    }
}

/**
 * Mini glass button for queue controls
 */
@Composable
fun GlassMiniButtonCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(50),
    isActive: Boolean = false,
    playerBackground: PlayerBackgroundStyle = PlayerBackgroundStyle.DEFAULT,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable BoxScope.() -> Unit,
) {
    val isOnDarkBackground = remember(playerBackground, isDarkTheme) {
        when (playerBackground) {
            PlayerBackgroundStyle.BLUR, PlayerBackgroundStyle.GRADIENT, PlayerBackgroundStyle.GLASS -> true
            PlayerBackgroundStyle.DEFAULT -> isDarkTheme
        }
    }

    val backgroundBrush = if (isActive) {
        Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )
        )
    } else {
        if (isOnDarkBackground) {
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.14f),
                    Color.White.copy(alpha = 0.06f)
                )
            )
        } else {
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.75f),
                    Color.White.copy(alpha = 0.50f)
                )
            )
        }
    }

    val borderBrush = if (isActive) {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.4f),
                Color.White.copy(alpha = 0.1f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = if (isOnDarkBackground) 0.25f else 0.7f),
                Color.White.copy(alpha = if (isOnDarkBackground) 0.06f else 0.2f)
            )
        )
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = if (isActive) 8.dp else 4.dp,
                shape = shape,
                clip = false,
                ambientColor = Color.Black.copy(alpha = 0.15f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            .clip(shape)
            .background(backgroundBrush)
            .border(BorderStroke(1.dp, borderBrush), shape),
        content = content
    )
}
