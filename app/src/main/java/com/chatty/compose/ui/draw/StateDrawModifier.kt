package com.chatty.compose.ui.draw

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.dp
import com.chatty.compose.ui.theme.ok

fun Modifier.drawLoginStateRing() = this.then(
    object : DrawModifier {
        override fun ContentDrawScope.draw() {
            val circleRadius = 20.dp.toPx()
            drawContent()
            drawCircle(
                color = Color.White,
                radius = circleRadius,
                center = Offset(drawContext.size.width - circleRadius , drawContext.size.height - circleRadius)
            )
            drawCircle(
                color = ok,
                radius = circleRadius * 4 / 5,
                center = Offset(drawContext.size.width - circleRadius, drawContext.size.height - circleRadius)
            )
        }
    }
)