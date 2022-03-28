package com.chatty.compose.network.jsonObjects

import kotlinx.serialization.Serializable

/**
 * 会话成员的信息
 */
@Serializable
data class MemberInfo(
    val uid: String,
    val nickname: String?,
    val avatar: String?,
    val role: Int // role 表示成员在会话的身份，0 —— 普通， 1 —— 管理员， 2 —— 创始人
)

@Serializable
data class Members(
    val members: List<MemberInfo>
)

/**
 * 一个对话的结构信息
 */
@Serializable
data class ConversationsInfo(
    val id: Long, // 会话 id
    val name: String?, // 会话名
    val mid: Long?, // 消息 id
    val sender: String?, // 最后的发送者
    val nickname: String?, // 昵称
    val time: Long?, // 时间戳
    val msg: String?, // 消息
    val unreadCount: Int // 未读消息数量
)

@Serializable
data class Conversations(
    val conversations: List<ConversationsInfo>
)

@Serializable
data class AuthResult(
    val accessToken: String
)

@Serializable
data class PrepareUploadResult(
    val id: String
)

@Serializable
data class ChecknameResult(
    val result: Int
)

// 从服务器返回用户的信息
@Serializable
data class UserInfo(
    val uid: String,
    val creationTime: Long?,
    val avatar: String?, // 用户的头像 id，不包含后缀
    val isContact: Boolean?,
    val nickname: String?,
    val isBlocked: Boolean?
)

// 查询所有符合指定名字的用户
@Serializable
data class UserList(
    val users: List<UserInfo>
)

@Serializable
data class UserUpdate(
    val avatar: String?
)

@Serializable
data class AuthInfo(
    val uid: String,
    val pwd: String
)

@Serializable
data class RegisterInfo(
    val uid: String,
    val pwd: String
)
