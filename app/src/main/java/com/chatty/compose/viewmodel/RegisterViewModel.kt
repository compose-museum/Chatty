package com.chatty.compose.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatty.compose.common.AuthStatus
import com.chatty.compose.common.NameStatus
import com.chatty.compose.common.Utils
import com.chatty.compose.database.UserRepository
import com.chatty.compose.database.bean.User
import com.chatty.compose.network.Api
import com.chatty.compose.network.jsonObjects.AuthInfo
import com.chatty.compose.network.jsonObjects.RegisterInfo
import com.chatty.compose.network.jsonObjects.UserUpdate
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class RegisterViewModel(
    private val repository: UserRepository
): ViewModel() {

    var nameStatus = mutableStateOf(NameStatus.OK)
    var registerStatus = mutableStateOf(AuthStatus.UNAUTH)
    var tempUser = mutableStateOf<TempInfo?>(null)

    fun checkName(uid: String) {
        viewModelScope.launch {
            try {
                val result = Api.checkName(uid).result
                println("result $result")
                when (result) {
                    0 -> nameStatus.value = NameStatus.OK
                    1 -> nameStatus.value = NameStatus.MALFORMED
                    2 -> nameStatus.value = NameStatus.INVALID
                }
            } catch (e: Exception) {
                nameStatus.value = NameStatus.UNAVAILABLE
                e.printStackTrace()
                Log.d("ERROR", "发送 checkname api 请求错误")
            }
        }
    }

    fun register(uid: String, pwd: String, context: Context) {
        registerStatus.value = AuthStatus.AUTHING
        viewModelScope.launch {
            try {
                Api.register(RegisterInfo(uid, pwd))
                val token = Api.auth(AuthInfo(uid, pwd))
                val timestamp = System.currentTimeMillis()

//              暂时去掉本地数据库操作
//                if (tempUser.value?.avatarId == null) {
//                    repository.insertUser(
//                        User(uid, null, token.accessToken, timestamp)
//                    )
//                } else {
//                    val user = User(
//                        username = uid,
//                        avatar = tempUser.value!!.avatar,
//                        accessToken = token.accessToken,
//                        timestamp = tempUser.value!!.timestamp
//                    )
//                    Api.upload(
//                        tempUser.value!!.avatarId,
//                        tempUser.value!!.avatar,
//                        Utils.getAvatar(context, tempUser.value!!.avatar),
//                        user.accessToken!!
//                    )
//                    Api.updateUser(UserUpdate(tempUser.value!!.avatarId), token.accessToken)
//                    repository.insertUser(user)
//                }
                registerStatus.value = AuthStatus.AUTHED
                tempUser.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("ERROR", "注册用户时发生错误：${e.message} ${e.cause}")
                registerStatus.value = AuthStatus.FALSE
            }
        }
    }

    fun createLocalAvatar(uri: Uri, context: Context) {
        val avatarPath = Utils.getAvatarPath(context)
        if (!File(avatarPath).exists())
            File(avatarPath).mkdirs()
        viewModelScope.launch {
            try {
                // 从服务器获取用户的唯一头像 uuid
                val avatarId = Api.prepareUpload().id

                // 获取用户从手机中选择的图片的后缀名
                val fileType = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(context.contentResolver.getType(uri))
                val timestamp = System.currentTimeMillis()

                if (fileType == null) {
                    Toast.makeText(context, "文件名不支持", Toast.LENGTH_LONG).show()
                    throw Exception("未获取到文件格式")
                } else {
                    tempUser.value = TempInfo(
                        avatarId = avatarId,
                        avatar = "$avatarId.$fileType",
                        fileType = Utils.getImageFileType(avatarId),
                        timestamp = timestamp
                    )
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val outputStream: OutputStream = FileOutputStream(File(avatarPath, "$avatarId.$fileType"))
                    inputStream!!.copyTo(outputStream)
                    Log.d("", "写入到本地头像成功")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("ERROR", "获取头像 uuid 时发生错误, 或写入本地头像失败：${e.message} ${e.cause}")
                Toast.makeText(context, "上传头像失败", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun clearTempUser(context: Context) {
        tempUser.value?.avatarId?.let {
            val avatarFile = "${Utils.getAvatarPath(context)}/$it"
            File(avatarFile).delete()
        }
        tempUser.value = null
    }
}

data class TempInfo(
    val avatarId: String, // 存储头像的名字 （id） "abc.png' -> abc
    val avatar: String, // 存储头像的完整名字 id + 后缀 "abc.png"
    val fileType: String, // 存储头像的文件类型 jpg/png/jpeg/
    val timestamp: Long
)