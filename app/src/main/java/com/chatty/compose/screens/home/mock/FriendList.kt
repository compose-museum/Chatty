package com.chatty.compose.screens.home.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chatty.compose.R
import com.chatty.compose.bean.MessageItemData
import com.chatty.compose.bean.UserProfileData

val friends = listOf(
    UserProfileData(R.drawable.ava1, "John", "I miss you", gender = "男", uid = "1024"),
    UserProfileData(R.drawable.ava2, "Tony", "冬至"),
    UserProfileData(R.drawable.ava3, "Meth", "“做最好的准备  也做最坏的打算”"),
    UserProfileData(R.drawable.ava4, "Beatriz", "水母只能在深海度过相对失败的一生"),
    UserProfileData(R.drawable.ava5, "香辣鸡腿堡", "请向前走，不要在此停留。konpaku.cn"),
    UserProfileData(R.drawable.ava6, "爱丽丝", "逝者如斯夫，不舍昼夜"),
    UserProfileData(R.drawable.ava7, "Horizon", "对韭当割，人生几何。"),
    UserProfileData(R.drawable.ava8, "鲤鱼", "未曾谋面的也终将会相遇的，慢慢来吧，慢慢约会吧\uD83D\uDC31"),
    UserProfileData(R.drawable.ava9, "小太阳", "这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。"),
    UserProfileData(R.drawable.ava10, "时光", "回到过去"),
    UserProfileData(R.drawable.ava1, "Bob", "I miss you"),
    UserProfileData(R.drawable.ava2, "XinLa", "冬至"),
    UserProfileData(R.drawable.ava3, "Nancy", "“做最好的准备  也做最坏的打算”"),
    UserProfileData(R.drawable.ava4, "Nini", "水母只能在深海度过相对失败的一生"),
    UserProfileData(R.drawable.ava5, "Brain", "请向前走，不要在此停留。konpaku.cn"),
    UserProfileData(R.drawable.ava6, "十香", "逝者如斯夫，不舍昼夜"),
    UserProfileData(R.drawable.ava7, "Bird", "对韭当割，人生几何。"),
    UserProfileData(R.drawable.ava8, "泰拉瑞亚", "未曾谋面的也终将会相遇的，慢慢来吧，慢慢约会吧\uD83D\uDC31"),
    UserProfileData(R.drawable.ava9, "奥风", "这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。"),
    UserProfileData(R.drawable.ava10, "0x5f3759df", "科学的尽头是玄学")
)

val recentMessages = mutableStateListOf(
    MessageItemData(friends[0], "吃了吗？", 2),
    MessageItemData(friends[1], "你说的对，我也是这么想的", 1),
    MessageItemData(friends[2], "Compose实在太方便了", 3),
    MessageItemData(friends[3], "嗨喽好久不见，最近在忙啥呢", 4),
    MessageItemData(friends[4], "目前确实可以这样搞", 2),
    MessageItemData(friends[5], "在嘛？有空吗？", 4),
    MessageItemData(friends[6], "有一说一，确实"),
    MessageItemData(friends[10], "啊对对对"),
    MessageItemData(friends[11], "我觉得香辣鸡腿堡是真的好吃"),
    MessageItemData(friends[12], "等会见")
)

var displayMessages by mutableStateOf( recentMessages.toMutableList() )