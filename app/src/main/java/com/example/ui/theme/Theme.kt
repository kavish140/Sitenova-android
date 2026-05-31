package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = PrimaryWhite,
    onPrimary = DarkBackground,
    secondary = AccentSky,
    onSecondary = DarkBackground,
    tertiary = AccentEmerald,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = BorderSlate,
    error = ErrorRed
)

// Fallback light scheme (but styled to keep the clean minimalism)
private val LightColorPalette = lightColorScheme(
    primary = DarkBackground,
    onPrimary = Color.White,
    secondary = AccentSky,
    onSecondary = DarkBackground,
    tertiary = AccentEmerald,
    background = Color.White,
    onBackground = Color(0xFF111827),
    surface = Color(0xFFF3F4F6),
    onSurface = Color(0xFF111827),
    outline = Color(0xFFE5E7EB),
    error = Color(0xFFDC2626)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false, // Force light mode
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
