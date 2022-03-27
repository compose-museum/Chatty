package com.chatty.compose.screens.chatty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chatty.compose.R

@Composable
fun Chatty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        MyTopBar()

        val friends = listOf(
            FriendItemData(R.drawable.ava1, "John", "I miss you", 2),
            FriendItemData(R.drawable.ava2, "Tony", "冬至", 5),
            FriendItemData(R.drawable.ava3, "Meth", "“做最好的准备  也做最坏的打算”", 16),
            FriendItemData(R.drawable.ava4, "Beatriz", "水母只能在深海度过相对失败的一生", 17),
            FriendItemData(R.drawable.ava5, "香辣鸡腿堡", "请向前走，不要在此停留。konpaku.cn", 2),
            FriendItemData(R.drawable.ava6, "01x", "逝者如斯夫，不舍昼夜", 4),
            FriendItemData(R.drawable.ava7, "Horizon", "对韭当割，人生几何。", 99),
            FriendItemData(R.drawable.ava8, "鲤鱼", "未曾谋面的也终将会相遇的，慢慢来吧，慢慢约会吧\uD83D\uDC31"),
            FriendItemData(R.drawable.ava9, "小太阳", "这个人很懒，什么都没留下。"),
            FriendItemData(R.drawable.ava10, "时光", "回到过去")
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp),
        ) {
            itemsIndexed(friends) { index, item ->
                FriendItem(item.avatarRes, item.friendName, item.lastMsg, item.unreadCount)
            }
        }
    }
}
