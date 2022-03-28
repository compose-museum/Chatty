package com.chatty.compose.network

import com.chatty.compose.network.jsonObjects.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.*
import retrofit2.http.*
import okhttp3.ResponseBody

interface ApiInterface {
    /**
     * 获取会话列表
     */
    @GET(ApiType.conversation)
    fun getConversations(
        @Header("Authorization") token: String
    ) : Call<Conversations>

    /**
     * 获取会话中的成员
     */
    @GET("${ApiType.conversation}/{id}/member")
    fun getConversationMember(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Call<Members>

    /**
     * 查询用户
     */
    @GET("${ApiType.user}/{id}?search=true")
    fun searchUsers(
        @Path("id") id: String
    ) : Call<UserList>

    /**
     * 获取用户个人信息
     */
    @GET(ApiType.user)
    fun user(
        @Header("Authorization") token: String
    ) : Call<UserInfo>

    @GET("${ApiType.image}/{id}")
    fun getImageType(
        @Path("id") id: String
    ) : Call<ResponseBody>

    /**
     *  检测当前的用户名是否合法
     */
    @GET("${ApiType.checkName}/{id}")
    fun checkName(
        @Path("id") name: String
    ) : Call<ChecknameResult>

    @Headers(HEADER_TYPE_JSON)
    @POST(ApiType.auth)
    fun auth(
        @Body authInfo: AuthInfo
    ) : Call<AuthResult>

    @Headers(HEADER_TYPE_JSON)
    @POST(ApiType.user)
    fun register(
        @Body registerInfo: RegisterInfo
    ) : Call<ResponseBody>

    @GET(ApiType.image)
    fun prepareUpload() : Call<PrepareUploadResult>

    @POST("${ApiType.image}/{id}")
    fun image(
        @Path("id") id: String,
        @Body requestBody: RequestBody,
        @Header("Authorization") token: String
    ) : Call<ResponseBody>

    @Headers(HEADER_TYPE_JSON)
    @PATCH(ApiType.user)
    fun updateUser(
        @Body update: UserUpdate,
        @Header("Authorization") token: String
    ) : Call<ResponseBody>

    companion object {
        const val BASE_URL = "https://mockapi.com"
        private val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }.asConverterFactory("application/json".toMediaType())

        fun create(): ApiInterface {
            return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    json
                )
                .build()
                .create(ApiInterface::class.java)
        }
    }
}
