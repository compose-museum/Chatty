package com.chatty.compose.bean


data class FriendMessageItemData(
    val avatarRes: Int,
    val friendName: String,
    val lastMsg: String,
    val unreadCount: Int = 0
)