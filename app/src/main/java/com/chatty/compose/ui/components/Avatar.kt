package com.chatty.compose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.chatty.compose.R
import com.chatty.compose.common.Utils
import com.chatty.compose.database.bean.User

@Composable
fun Avatar(
    painter: Painter,
    modifier: Modifier = Modifier
) {
    CircleShapeImage(
        painter = painter,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun Avatar(
    user: User?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    if (user?.avatar != null) {
        CircleShapeImage(
            painter = rememberImagePainter(
                data = Utils.getAvatar(context, user.avatar),
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        CircleShapeImage(
            painter = painterResource(id = R.drawable.person),
            modifier = modifier,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun Avatar(
    url: String?,
    modifier: Modifier = Modifier
) {
    if (url != null) {
        CircleShapeImage(
            painter = rememberImagePainter(
                data = url,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        CircleShapeImage(
            painter = painterResource(id = R.drawable.person),
            modifier = modifier,
            contentScale = ContentScale.Fit
        )
    }
}
