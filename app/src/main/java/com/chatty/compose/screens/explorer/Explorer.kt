package com.chatty.compose.screens.explorer

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalModalBottomSheetState
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun Explorer() {

    val lazyState = rememberLazyListState()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)


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

    CompositionLocalProvider(LocalModalBottomSheetState provides bottomSheetState) {
        ModalBottomSheetLayout(
            sheetContent = { CreatePost() },
            sheetState = bottomSheetState,
            modifier = Modifier.padding(WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.chattyColors.backgroundColor)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = lazyState
                ) {
                    item { Spacer(Modifier.statusBarsPadding()) }
                    item {
                        CenterRow(
                            modifier = Modifier
                                .padding(14.dp)
                                .alpha(1 - topBarAlpha)
                        ) {
                            Text(
                                text = "探索新鲜事",
                                style = MaterialTheme.typography.h4,
                                color = MaterialTheme.chattyColors.textColor
                            )
                            Spacer(Modifier.weight(1f))
                            CircleShapeImage(size = 48.dp, painter = painterResource(id = R.drawable.ava4))
                        }
                        Divider(modifier = Modifier.fillMaxWidth())
                    }
                    items(20) {
                        SocialItem(R.drawable.ava5, name = "香辣鸡腿堡")
                    }
                }
                ExplorerTopBar(topBarAlpha)
                ExplorerFab(
                    boxScope = this,
                    targetState = topBarAlpha,
                    editAction = {
                        scope.launch { bottomSheetState.show() }
                    }
                ) {
                    scope.launch { lazyState.scrollToItem(0) }
                }
            }
        }
    }
}

@Composable
fun SocialItem(
    avaRes: Int,
    name: String,
    time: String = "22 分钟之前",
    content: String = "刚高考完，来广州打暑假工，今天，人生第一次工作，站了8个小时腿都酸了，不敢和客人讲话，害怕店长看我的眼神，生怕做的不合客人的意，现在躺在床上想着以前在家打LOL的日子。虽然现在累的闭眼都能睡着，但过的很多意义，勇敢点，大胆点，自信点，加油，这两个月我可以。"
) {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        CenterRow {
            CircleShapeImage(40.dp, painterResource(avaRes))
            WidthSpacer(value = 4.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.chattyColors.textColor
                )
                HeightSpacer(value = 2.dp)
                Text(
                    text = time,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.alpha(0.5f),
                    color = MaterialTheme.chattyColors.textColor
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.MoreVert, null, tint = MaterialTheme.chattyColors.iconColor)
            }
        }
        HeightSpacer(value = 4.dp)
        Text(
            text = content,
            color = MaterialTheme.chattyColors.textColor
        )
        HeightSpacer(value = 4.dp)
        CenterRow {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painterResource(R.drawable.heart), null, modifier = Modifier.size(24.dp), tint = MaterialTheme.chattyColors.iconColor)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painterResource(id = R.drawable.comment), null, modifier = Modifier.size(24.dp), tint = MaterialTheme.chattyColors.iconColor)
            }
        }
    }
}
