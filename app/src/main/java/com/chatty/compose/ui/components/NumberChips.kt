package com.chatty.compose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberChips(
    number: Int,
    color: Color = Color.Red
) {
    Surface(
        shape = CircleShape,
        color = color,
    ) {
        Text(
            text = number.toString(),
            modifier = Modifier.padding(
                start = 6.dp,
                end = 6.dp,
                top = 2.dp,
                bottom = 2.dp
            ),
            style = TextStyle(
                fontWeight = FontWeight.W900,
                fontSize = 12.sp,
                letterSpacing = 0.15.sp,
                color = Color.White
            )
        )
    }
}
