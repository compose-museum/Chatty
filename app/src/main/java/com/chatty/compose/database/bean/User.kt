package com.chatty.compose.database.bean

import androidx.room.*

@Entity
data class User(
    @PrimaryKey
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "avatar") val avatar: String?,
    @ColumnInfo(name = "accessToken") val accessToken: String?,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)