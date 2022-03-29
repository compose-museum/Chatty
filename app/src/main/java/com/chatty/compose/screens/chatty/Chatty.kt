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
import com.chatty.compose.screens.chatty.mock.friends

@Composable
fun Chatty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        ChattyTopBar()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp),
        ) {
            itemsIndexed(friends) { index, item ->
                FriendMessageItem(item.avatarRes, item.friendName, item.lastMsg, item.unreadCount)
            }
        }
    }
}
