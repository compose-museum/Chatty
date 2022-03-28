package com.chatty.compose.network

import com.chatty.compose.network.jsonObjects.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import retrofit2.Call

class MockApi {
    fun getConversations(token: String): Conversations {
        TODO("Not yet implemented")
    }

    fun getConversationMember(token: String, id: String): Members {
        TODO("Not yet implemented")
    }

    fun searchUsers(id: String): UserList {
        TODO("Not yet implemented")
    }

    fun user(token: String): UserInfo {
        TODO("Not yet implemented")
    }

    fun getImageType(id: String): ResponseBody {
        TODO("Not yet implemented")
    }

    fun checkName(name: String): ChecknameResult {
        return ChecknameResult(0)
    }

    fun auth(authInfo: AuthInfo): AuthResult {
        return AuthResult("12345678")
    }

    fun register(registerInfo: RegisterInfo): ResponseBody {
        return object : ResponseBody() {
            override fun contentLength(): Long {
                return 0
            }

            override fun contentType(): MediaType? {
                return "text".toMediaTypeOrNull()
            }

            override fun source(): BufferedSource {
                return Buffer()
            }

        }
    }

    fun prepareUpload(): PrepareUploadResult {
        TODO("Not yet implemented")
    }

    fun image(id: String, requestBody: RequestBody, token: String): ResponseBody {
        TODO("Not yet implemented")
    }

    fun updateUser(update: UserUpdate, token: String): ResponseBody {
        TODO("Not yet implemented")
    }
}