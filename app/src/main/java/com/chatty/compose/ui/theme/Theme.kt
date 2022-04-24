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
    val conversationBubbleBg: Color
    val conversationBubbleBgMe: Color
    val conversationText: Color
    val conversationTextMe: Color
    val conversationAnnotatedText: Color
    val conversationAnnotatedTextMe: Color
    val conversationInputSelector: Color
    val conversationInputSelectorSelected: Color
    val conversationHintText: Color
    val disabledContent: Color
}

private object LightColors : IChattyColors {
    override val backgroundColor @Composable get() =  Color(0xFFF8F8F8)
    override val textColor @Composable get() = Color.Black
    override val iconColor @Composable get() = Color.Black
    override val conversationBubbleBg = Color.White
    override val conversationBubbleBgMe = Color.LightGray
    override val conversationText = Color.Black
    override val conversationTextMe = Color.Black
    override val conversationAnnotatedText = Color(0xFF03A9F4)
    override val conversationAnnotatedTextMe = Color(0xFFDAEAF1)
    override val conversationInputSelector = Color.Gray.copy(0.7f)
    override val conversationInputSelectorSelected = Color.Black
    override val conversationHintText = Color.Gray.copy(0.7f)
    override val disabledContent = Color.LightGray.copy(0.8f)
}

private object DarkColors : IChattyColors {
    override val backgroundColor @Composable get() = Color(0xFF464547)
    override val textColor @Composable get() = Color.White
    override val iconColor @Composable get() = Color.White
    override val conversationBubbleBg = Color.Black
    override val conversationBubbleBgMe = Color.LightGray
    override val conversationText = Color.White
    override val conversationTextMe = Color.Black
    override val conversationAnnotatedText = Color(0xFFDAEAF1)
    override val conversationAnnotatedTextMe = Color(0xFF03A9F4)
    override val conversationInputSelector = Color.White.copy(0.8f)
    override val conversationInputSelectorSelected = Color.White
    override val conversationHintText = Color.White.copy(0.3f)
    override val disabledContent = Color.White.copy(0.5f)
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

    fun toggleToLightColor() { isLight = true }
    fun toggleToDarkColor() { isLight = false }


    override val backgroundColor @Composable get() = animatedValue(_curColors.backgroundColor)
    override val textColor @Composable get() = animatedValue(_curColors.textColor)
    override val iconColor @Composable get() = animatedValue(_curColors.iconColor)
    override val conversationBubbleBg get() = _curColors.conversationBubbleBg
    override val conversationBubbleBgMe get() = _curColors.conversationBubbleBgMe
    override val conversationText get() = _curColors.conversationText
    override val conversationTextMe get() = _curColors.conversationTextMe
    override val conversationAnnotatedText get() = _curColors.conversationAnnotatedText
    override val conversationAnnotatedTextMe get() = _curColors.conversationAnnotatedTextMe
    override val conversationInputSelector get() = _curColors.conversationInputSelector
    override val conversationInputSelectorSelected get() = _curColors.conversationInputSelectorSelected
    override val conversationHintText get() = _curColors.conversationHintText
    override val disabledContent get() = _curColors.disabledContent
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

    val chattyColors = ChattyColors()

    val colors = if (darkTheme) {
        chattyColors.toggleToDarkColor()
        DarkColorPalette
    } else {
        chattyColors.toggleToLightColor()
        LightColorPalette
    }

    CompositionLocalProvider(LocalChattyColors provides chattyColors) {
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