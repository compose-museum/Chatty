package com.chatty.compose.screens.explorer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        color = MaterialTheme.chattyColors.backgroundColor,
        shadowElevation = 14.dp
    ) {
        Box {
            CenterRow(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircleShapeImage(size = 30.dp, painter = painterResource(id = R.drawable.ava4), contentScale = ContentScale.Crop)
                WidthSpacer(6.dp)
                Text(text = "探索新鲜事中", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.chattyColors.textColor)
            }
            Divider(Modifier.fillMaxWidth().align(Alignment.BottomCenter), color = Color(0xFF0079D3), thickness = 2.dp)
        }
    }
}
