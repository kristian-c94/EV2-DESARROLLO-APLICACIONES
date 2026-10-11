package com.duoc.madera.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = WoodPrimary,
    onPrimary = Color.White,
    secondary = WoodSecondary,
    onSecondary = Color.White,
    background = WoodBackground,
    surface = WoodSurface,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    outline = WoodCardBorder
)

@Composable
fun EnciclopediaMaderasTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}