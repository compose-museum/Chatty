package com.chatty.compose.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val LocalChattyColors = compositionLocalOf {
    ChattyColors()
}

val MaterialTheme.chattyColors: ChattyColors
    @Composable
    @ReadOnlyComposable
    get() = LocalChattyColors.current

class ChattyColors {
    var isLight by mutableStateOf(false)
        private set

    val backgroundColor: Color
        @Composable
        get() = animateColorAsState(targetValue = if (isLight) Color.White else Color.Black, tween(700)).value

    val textColor: Color
        @Composable
        get() = animateColorAsState(targetValue = if (isLight) Color.Black else Color.White, tween(700)).value

    val iconColor: Color
        @Composable
        get() = animateColorAsState(targetValue = if (isLight) Color.Black else Color.White, tween(700)).value

    fun toggleTheme() {
        isLight = !isLight
    }
}

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ChattyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalChattyColors provides ChattyColors()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}