package com.chatty.compose.network

import android.net.Uri
import android.os.Build
import com.chatty.compose.BuildConfig
import com.chatty.compose.common.Utils
import com.chatty.compose.network.jsonObjects.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.HttpException
import java.io.File

object Api {

    private val mockApi = MockApi()
    private val api = ApiInterface.create()


    /**
     * 认证用户，如果成功返回 accessToken
     */
    suspend fun auth(authInfo: AuthInfo): AuthResult {
        if (BuildConfig.DEBUG) {
            return mockApi.auth(authInfo)
        }
        return call {
            api.auth(authInfo)
        }
    }

    suspend fun register(registerInfo: RegisterInfo): ResponseBody {
        if (BuildConfig.DEBUG) {
            return mockApi.register(registerInfo)
        }
        return call {
            api.register(registerInfo)
        }
    }

    suspend fun checkName(uid: String): ChecknameResult {
        if (BuildConfig.DEBUG) {
            return mockApi.checkName(uid)
        }
        return call {
            api.checkName(uid)
        }
    }

    suspend fun prepareUpload():PrepareUploadResult  {
        if (BuildConfig.DEBUG) {
            mockApi.prepareUpload()
        }
        return call {
            api.prepareUpload()
        }
    }

    suspend fun upload(imageId: String, filename: String, uri: Uri, token: String): ResponseBody {
        val mimeType = Utils.getMimeType(uri)
        val reqFile = MultipartBody.Builder()
            .addFormDataPart("file", filename, File(uri.path).asRequestBody(mimeType?.toMediaType()))
            .build()
        if (BuildConfig.DEBUG) {
            mockApi.image(imageId, reqFile, "Bearer $token")
        }
        return call {
            api.image(imageId, reqFile, "Bearer $token")
        }
    }

    suspend fun updateUser(update: UserUpdate, token: String) = call {
        api.updateUser(update, "Bearer $token")
    }

    suspend fun getUserInfo(token: String) = call {
        api.user("Bearer $token")
    }

    suspend fun getImageType(id: String) = callRaw {
        api.getImageType(id)
    }

    suspend fun searchUsers(id: String) = call {
        api.searchUsers(id)
    }

    suspend fun getConversations(token: String) = call {
        api.getConversations("Bearer $token")
    }

    suspend fun getConversationMember(token: String, conversationId: Long) = call {
        api.getConversationMember("Bearer $token", conversationId.toString())
    }

    private suspend fun <T> call(block: () -> Call<T>): T = coroutineScope {
        withContext(Dispatchers.IO) {
            val ret = block().execute()
            if (ret.isSuccessful)
                ret.body()!!
            else {
                println("[${ret.raw().request.url}] ${ret.code()}: ${ret.message()}")
                throw HttpException(ret)
            }
        }
    }

    private suspend fun <T> callRaw(block: () -> Call<T>): Response = coroutineScope {
        withContext(Dispatchers.IO) {
            val ret = block().execute()
            if (ret.isSuccessful)
                ret.raw()
            else {
                println("[${ret.raw().request.url}] ${ret.code()}: ${ret.message()}")
                throw HttpException(ret)
            }
        }
    }

}
