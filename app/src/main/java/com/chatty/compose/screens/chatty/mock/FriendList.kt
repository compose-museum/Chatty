package com.chatty.compose.screens.chatty.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chatty.compose.R
import com.chatty.compose.bean.MessageItemData
import com.chatty.compose.bean.UserProfileData
import com.chatty.compose.screens.chatty.FriendMessageItem

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
    UserProfileData(R.drawable.ava10, "竹蜻蜓", "回到过去")
)

val recentMessages = mutableStateListOf(
    MessageItemData(friends[0], "I miss you", 2),
    MessageItemData(friends[1], "冬至", 5),
    MessageItemData(friends[2], "“做最好的准备  也做最坏的打算”", 16),
    MessageItemData(friends[3], "水母只能在深海度过相对失败的一生", 17),
    MessageItemData(friends[4], "请向前走，不要在此停留。konpaku.cn", 2),
    MessageItemData(friends[5], "逝者如斯夫，不舍昼夜", 4),
    MessageItemData(friends[6], "对韭当割，人生几何。", 99),
    MessageItemData(friends[7], "未曾谋面的也终将会相遇的，慢慢来吧，慢慢约会吧\uD83D\uDC31"),
    MessageItemData(friends[8], "这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。这个人很懒，什么都没留下。"),
    MessageItemData(friends[9], "回到过去"),
    MessageItemData(friends[10], "I miss you", 2),
    MessageItemData(friends[11], "冬至", 5),
    MessageItemData(friends[12], "“做最好的准备  也做最坏的打算”", 16)
)

var displayMessages by mutableStateOf( recentMessages.toMutableList() )