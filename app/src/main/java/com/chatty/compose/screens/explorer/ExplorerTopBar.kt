package com.chatty.compose.screens.explorer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.theme.chattyColors

@Composable
fun ExplorerTopBar(
    alpha: Float
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha),
        color = Color.Gray,
    ) {
        CenterRow(
            modifier = Modifier
                .statusBarsPadding()
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CircleShapeImage(size = 30.dp, painter = painterResource(id = R.drawable.ava4), contentScale = ContentScale.Crop)
            WidthSpacer(6.dp)
            Text(text = "探索新鲜事中", style = MaterialTheme.typography.h6, color = MaterialTheme.chattyColors.textColor)
        }
    }
}
