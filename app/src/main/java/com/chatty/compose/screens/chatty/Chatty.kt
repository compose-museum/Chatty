package com.chatty.compose.screens.chatty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.chatty.compose.screens.chatty.mock.displayMessages
import com.chatty.compose.ui.theme.chattyColors

@Composable
fun Chatty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.chattyColors.backgroundColor)
    ) {
        ChattyTopBar()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            itemsIndexed(displayMessages, key = { _, item ->
                item.mid
            }) { _, item ->
                FriendMessageItem(item.userProfile.avatarRes, item.userProfile.nickname, item.lastMsg, item.unreadCount)
            }
        }
    }
}
