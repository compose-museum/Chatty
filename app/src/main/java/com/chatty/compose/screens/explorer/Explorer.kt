package com.chatty.compose.screens.explorer

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.*
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController
import kotlinx.coroutines.launch

@Composable
fun Explorer() {

    val lazyState = rememberLazyListState()
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
    val firstItemSize by remember {
        derivedStateOf {
            if (lazyState.layoutInfo.visibleItemsInfo.isNotEmpty())
                lazyState.layoutInfo.visibleItemsInfo[0].size
            else null
        }
    }

    val topBarAlpha by remember {
        derivedStateOf {
            if (lazyState.firstVisibleItemIndex == 0) {
                firstItemSize?.let {
                    lazyState.firstVisibleItemScrollOffset.dp / it.dp
                } ?: 0f
            } else 1f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.chattyColors.backgroundColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyState,
            contentPadding = WindowInsets.statusBars.asPaddingValues()
        ) {
            item {
                CenterRow(
                    modifier = Modifier
                        .padding(14.dp)
                        .alpha(1 - topBarAlpha)
                ) {
                    Text(
                        text = "探索新鲜事",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.chattyColors.textColor
                    )
                    Spacer(Modifier.weight(1f))
                    CircleShapeImage(size = 48.dp, painter = painterResource(id = R.drawable.ava4))
                }
                Divider(modifier = Modifier.fillMaxWidth())
            }
            items(20) {
                SocialItem(
                    R.drawable.ava5,
                    name = "香辣鸡腿堡"
                )
            }
        }
        ExplorerTopBar(topBarAlpha)
        ExplorerFab(
            boxScope = this,
            targetState = topBarAlpha,
            editAction = {
                scope.launch { navController.navigate(AppScreen.createPost) }
            }
        ) {
            scope.launch { lazyState.scrollToItem(0) }
        }
    }
}

@Composable
fun SocialItem(
    avaRes: Int,
    name: String,
    time: String = "22 分钟之前",
    content: String = "带着最坚定的“决心”，最后的决战就在眼前了。 然而，攸惚间，却回到了最开始的地方。 音乐响起，相遇过的身影一一出现。 娓娓讲起，一切的开始。 然而……我们的前方…… 真的是一切的结束吗？ 得知了真相之后，这份“决心”…… 是否会有所松动？ 这份“决心”，又会将这个“世界”的命运带向何处？"
) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .clickable { }
            .padding(16.dp)
    ) {
        CenterRow {
            CircleShapeImage(40.dp, painterResource(avaRes))
            WidthSpacer(value = 4.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.chattyColors.textColor
                )
                HeightSpacer(value = 2.dp)
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.alpha(0.5f),
                    color = MaterialTheme.chattyColors.textColor
                )
            }
        }
        HeightSpacer(value = 4.dp)
        Text(
            text = content,
            color = MaterialTheme.chattyColors.textColor
        )
        CenterRow(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            IconButton(
                onClick = {

                }
            ) {
                Icon(Icons.Rounded.Favorite, null, tint = MaterialTheme.chattyColors.iconColor.copy(0.5f))
            }
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    Icons.Rounded.ChatBubble,
                    null,
                    tint = MaterialTheme.chattyColors.iconColor.copy(0.5f),
                )
            }
            IconButton(
                onClick = {
                    context.startActivity(shareIntent)
                }
            ) {
                Icon(Icons.Rounded.Share, null, tint = MaterialTheme.chattyColors.iconColor.copy(0.5f))
            }
        }
    }

}
