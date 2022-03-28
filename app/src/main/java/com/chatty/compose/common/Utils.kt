package com.chatty.compose.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.Window
import android.webkit.MimeTypeMap
import androidx.compose.ui.graphics.Color
import java.io.File
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    /**
     *  从手机图片中的 uri 中获取 mimeType -> image/jpeg
     */
    fun getMimeType(uri: Uri): String? {
        val ext = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        return MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(ext.lowercase(Locale.getDefault()))
    }

    fun getImageFileType(filename: String): String {
        return filename.substringAfterLast(".", "")
    }

    fun getAvatarPath(context: Context): String {
        return context.filesDir.absolutePath + "/avatar"
    }

    fun getAvatar(context: Context, filename: String): Uri {
        return Uri.fromFile(File("${getAvatarPath(context)}/$filename"))
    }

    fun stampToDate(timeMillis: Long): String? {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date(timeMillis)
        return simpleDateFormat.format(date)
    }
}
