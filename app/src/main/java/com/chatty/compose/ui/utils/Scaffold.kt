package com.chatty.compose.ui.utils

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.compositionLocalOf

val LocalScaffoldState = compositionLocalOf<ScaffoldState> {
    error("CompositionLocal LocalNavController not present")
}