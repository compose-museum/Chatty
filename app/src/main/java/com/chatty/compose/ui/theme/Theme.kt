package com.chatty.compose.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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


private val MD_LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseSurface = md_theme_light_inverseSurface,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
)


private val MD_DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseSurface = md_theme_dark_inverseSurface,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
)

@Composable
fun ChattyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val chattyColors = ChattyColors()
    if (darkTheme) {
        chattyColors.toggleToDarkColor()
    } else {
        chattyColors.toggleToLightColor()
    }
    CompositionLocalProvider(LocalChattyColors provides chattyColors) {
        MaterialTheme(
            content = content,
            colorScheme = if (chattyColors.isLight) MD_LightColors else MD_DarkColors
        )
    }
}

@Composable
private fun animatedValue(targetValue: Color) = animateColorAsState(
    targetValue = targetValue,
    tween(700)
).value