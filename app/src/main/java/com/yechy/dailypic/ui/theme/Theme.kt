package com.yechy.dailypic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColorScheme(
    primary = Green700,
    onPrimary = Color.White,
    secondary = Green700,
    onSecondary = Color.White,
    error = Red800

)

private val DarkThemeColors = darkColorScheme(
    primary = Red300,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkThemeColors else LightThemeColors,
        shapes = Shapes,
        content = content)
}