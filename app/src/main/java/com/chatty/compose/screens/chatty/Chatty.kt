package com.chatty.compose.screens.chatty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.chatty.compose.R
import com.chatty.compose.screens.chatty.mock.friends
import com.chatty.compose.screens.chatty.mock.recentMessages

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
                .fillMaxSize(),
        ) {
            itemsIndexed(recentMessages) { index, item ->
                FriendMessageItem(item.userProfile.avatarRes, item.userProfile.nickname, item.lastMsg, item.unreadCount)
            }
        }
    }
}
