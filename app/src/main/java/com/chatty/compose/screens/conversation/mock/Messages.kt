package com.chatty.compose.screens.conversation.mock

import com.chatty.compose.R
import com.chatty.compose.screens.conversation.Message


val initialMessages = listOf(
    Message(
        true,
        "搞定！下班！",
        "8:07 PM"
    ),
    Message(
        true,
        "哇撒，还有这种网站",
        "8:06 PM",
        R.drawable.sticker
    ),
    Message(
        false,
        "这里面干货挺多，你可以去学一下，",
        "8:05 PM"
    ),

    Message(
        false,
        "除了Flow，Rxjava、Livedata 等等都有类似方法转成Compose的状态，" +
                "然后你就可以在Composable观察了 你可以看看官方文档，另外推荐你Compose的学习网站 \uD83D\uDC49" +
                "https://milklab.dev/docs/",
        "8:05 PM"
    ),
    Message(
        false,
        "你可以用这个API： `Flow.collectAsState()` ",
        "8:04 PM"
    ),
    Message(
        true,
        "在不在？我遇到个问题，我的ViewModel返回一个Flow，我现在Compose中订阅这个Flow，但是知道该怎么写，" +
                "你知道吗？",
        "8:03 PM"
    )
)

