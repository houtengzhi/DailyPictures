package com.yechy.dailypic.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

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
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    MaterialTheme(
        colorScheme = when {
            dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
            dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
            darkTheme -> DarkThemeColors
            else -> LightThemeColors
        },
        shapes = Shapes,
        content = content
    )
}