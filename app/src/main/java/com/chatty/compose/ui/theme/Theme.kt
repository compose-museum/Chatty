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


interface IChattyColors {
    val backgroundColor @Composable get() = Color.Black
    val textColor @Composable get() = Color.Black
    val iconColor @Composable get() = Color.Black
    val ConversationBubbleBg: Color
    val ConversationBubbleBgMe: Color
    val ConversationText: Color
    val ConversationTextMe: Color
    val ConversationAnnotatedText: Color
    val ConversationAnnotatedTextMe: Color
    val ConversationInputSelector: Color
    val ConversationInputSelectorSelected: Color
    val ConversationHintText: Color
    val DisabledContent: Color
}

private object LightColors : IChattyColors {
    override val backgroundColor @Composable get() = Color(0xFFF8F8F8)
    override val textColor @Composable get() = Color.Black
    override val iconColor @Composable get() = Color.Black
    override val ConversationBubbleBg = Color.White
    override val ConversationBubbleBgMe = Color.LightGray
    override val ConversationText = Color.Black
    override val ConversationTextMe = Color.Black
    override val ConversationAnnotatedText = Color(0xFF03A9F4)
    override val ConversationAnnotatedTextMe = Color(0xFFDAEAF1)
    override val ConversationInputSelector = Color.Gray.copy(0.7f)
    override val ConversationInputSelectorSelected = Color.Black
    override val ConversationHintText = Color.Gray.copy(0.7f)
    override val DisabledContent = Color.LightGray.copy(0.8f)
}

private object DarkColors : IChattyColors {
    override val backgroundColor @Composable get() = Color(0xFF464547)
    override val textColor: Color @Composable get() = Color.White
    override val iconColor: Color @Composable get() = Color.White
    override val ConversationBubbleBg = Color.Black
    override val ConversationBubbleBgMe = Color.LightGray
    override val ConversationText = Color.White
    override val ConversationTextMe = Color.Black
    override val ConversationAnnotatedText = Color(0xFFDAEAF1)
    override val ConversationAnnotatedTextMe = Color(0xFF03A9F4)
    override val ConversationInputSelector = Color.White.copy(0.8f)
    override val ConversationInputSelectorSelected = Color.White
    override val ConversationHintText = Color.White.copy(0.3f)
    override val DisabledContent = Color.White.copy(0.5f)

}

class ChattyColors : IChattyColors {
    var isLight by mutableStateOf(true)
        private set

    private val _curColors by derivedStateOf {
        if (isLight) LightColors else DarkColors
    }


    fun toggleTheme() {
        isLight = !isLight
    }


    override val backgroundColor @Composable get() = animatedValue(_curColors.backgroundColor)
    override val textColor @Composable get() = animatedValue(_curColors.textColor)
    override val iconColor @Composable get() = animatedValue(_curColors.iconColor)
    override val ConversationBubbleBg get() = _curColors.ConversationBubbleBg
    override val ConversationBubbleBgMe get() = _curColors.ConversationBubbleBgMe
    override val ConversationText get() = _curColors.ConversationText
    override val ConversationTextMe get() = _curColors.ConversationTextMe
    override val ConversationAnnotatedText get() = _curColors.ConversationAnnotatedText
    override val ConversationAnnotatedTextMe get() = _curColors.ConversationAnnotatedTextMe
    override val ConversationInputSelector get() = _curColors.ConversationInputSelector
    override val ConversationInputSelectorSelected get() = _curColors.ConversationInputSelectorSelected
    override val ConversationHintText get() = _curColors.ConversationHintText
    override val DisabledContent get() = _curColors.DisabledContent
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

@Composable
private fun animatedValue(targetValue: Color) = animateColorAsState(
    targetValue = targetValue,
    tween(700)
).value