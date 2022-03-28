package com.chatty.compose.network

import com.chatty.compose.network.jsonObjects.MemberInfo

data class ConversationData(
    val id: Long, // 会话 id
    val name: String?, // 会话名
    val mid: Long?, // 消息 id
    val sender: String?, // 最后的发送者
    val nickname: String?, // 昵称
    val time: Long?, // 时间戳
    val msg: String?, // 消息
    val unreadCount: Int, // 未读消息数量
    val members: List<MemberInfo>
)
