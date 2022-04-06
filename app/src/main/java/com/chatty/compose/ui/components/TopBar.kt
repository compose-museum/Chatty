package com.chatty.compose.ui.components

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chatty.compose.ui.utils.LocalNavController

@Preview
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    start: @Composable () -> Unit = {},
    center: @Composable () -> Unit = {},
    end: @Composable () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    contentPadding: PaddingValues = WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues()
) {
    TopAppBar(
        contentPadding = contentPadding,
        backgroundColor = backgroundColor,
        elevation = elevation,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(Modifier.align(Alignment.CenterStart)) {
                start()
            }
            Box(Modifier.align(Alignment.Center)) {
                center()
            }
            Box(Modifier.align(Alignment.CenterEnd)) {
                end()
            }
        }
    }
}