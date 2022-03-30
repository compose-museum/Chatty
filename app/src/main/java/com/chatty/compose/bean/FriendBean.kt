package com.chatty.compose.bean

import android.os.Parcel
import android.os.Parcelable
import java.util.*


data class MessageItemData(
    val userProfile: UserProfileData,
    val lastMsg: String,
    val unreadCount: Int = 0
)

data class UserProfileData(
    val avatarRes: Int,
    val nickname: String,
    val motto: String,
    val gender: String? = null,
    val age: Int? = null,
    val phone: String? = null,
    val email: String? = null,
    val uid: String = UUID.randomUUID().toString(),
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(avatarRes)
        parcel.writeString(nickname)
        parcel.writeString(motto)
        parcel.writeString(gender)
        parcel.writeValue(age)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserProfileData> {
        override fun createFromParcel(parcel: Parcel): UserProfileData {
            return UserProfileData(parcel)
        }

        override fun newArray(size: Int): Array<UserProfileData?> {
            return arrayOfNulls(size)
        }
    }
}