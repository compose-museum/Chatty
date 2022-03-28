package com.chatty.compose.ui.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("CompositionLocal LocalNavController not present")
}
