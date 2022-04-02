package com.chatty.compose.ui.utils

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.compositionLocalOf

val LocalScaffoldState = compositionLocalOf<ScaffoldState> {
    error("CompositionLocal LocalScaffoldState not present")
}

@OptIn(ExperimentalMaterialApi::class)
val LocalModalBottomSheetState = compositionLocalOf<ModalBottomSheetState> {
    error("CompositionLocal LocalModalBottomSheetState not present")
}