package com.chatty.compose.common

enum class AuthStatus {
    UNAUTH, AUTHING, AUTHED, FALSE
}

enum class NameStatus {
    OK, MALFORMED, INVALID, UNAVAILABLE
}

enum class SearchStatus {
    UNSTARTED, SEARCHING, SEARCHED
}