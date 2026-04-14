package com.agroconecta.mobile.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = White,
    primaryContainer = GreenLight,
    onPrimaryContainer = GreenDark,
    secondary = OrangeAccent,
    onSecondary = White,
    secondaryContainer = OrangeLight,
    onSecondaryContainer = OrangeAccent,
    surface = White,
    onSurface = Black,
    surfaceVariant = GrayLight,
    onSurfaceVariant = GrayDark,
    outline = GrayBorder,
    background = White,
    onBackground = Black,
)

@Composable
fun AgroConectaTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = White.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
