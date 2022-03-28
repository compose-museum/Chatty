package com.chatty.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp

@Composable
fun CircleShapeImage(
    size: Dp,
    painter: Painter,
    contentScale: ContentScale = ContentScale.Fit
) {
    Surface(
        modifier = Modifier
            .size(size),
        shape = CircleShape
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = contentScale
        )
    }
}
