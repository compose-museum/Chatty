package com.chatty.compose.screens.contracts

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.chatty.compose.R
import com.chatty.compose.bean.UserProfileData
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.drawLoginStateRing


@Composable
fun UserProfile(user: UserProfileData) {
    val verticalScrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.chattyColors.backgroundColor)
        .verticalScroll(verticalScrollState)
        .systemBarsPadding()
        .padding(horizontal = 20.dp)
    ) {
        UserProfileHeader(user = user)
        HeightSpacer(value = 10.dp)
        UserProfileDetail(user)
        HeightSpacer(value = 20.dp)
        MoreFriendOptions()
        HeightSpacer(value = 10.dp)
        Button(
            onClick = {
                // 删除联系人
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = "删除联系人",
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Red
            )
        }
    }
}

fun fetchUserOnlineStatus(uid: String): Boolean {
    return true
}

@Composable
fun UserProfileHeader(user: UserProfileData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally){
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = user.avatarRes),
                contentDescription = "avatar",
                modifier = Modifier
                    .size(150.dp)
                    .drawLoginStateRing(fetchUserOnlineStatus(user.uid))
                    .clip(CircleShape)
            )
        }
        Text(
            text = user.nickname,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.chattyColors.textColor
        )
        HeightSpacer(value = 10.dp)
        Text(
            text = user.motto,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.chattyColors.textColor,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        HeightSpacer(value = 15.dp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.phone),
                    contentDescription = "phone",
                    tint = MaterialTheme.chattyColors.iconColor
                )
            }
            WidthSpacer(value = 30.dp)
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.message),
                    contentDescription = "phone",
                    tint = MaterialTheme.chattyColors.iconColor
                )
            }
            WidthSpacer(value = 30.dp)
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.video),
                    contentDescription = "phone",
                    tint = MaterialTheme.chattyColors.iconColor
                )
            }
        }
    }
}
@Composable
fun UserProfileDetail(user: UserProfileData) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "用户详情",
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.chattyColors.textColor
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
        )
        HeightSpacer(value = 10.dp)
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (genderTitleRef, ageTitleRef, phoneTitleRef, emailTitleRef) = createRefs()
            val (genderRef, ageRef, phoneRef, emailRef) = createRefs()
            val barrier = createEndBarrier(genderTitleRef, ageTitleRef, phoneTitleRef, emailTitleRef)
            val barrierDistance = 80.dp
            Text(
                text = "性别",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(genderTitleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                color = MaterialTheme.chattyColors.textColor
            )
            Text(
                text = "年龄",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(ageTitleRef) {
                    top.linkTo(genderTitleRef.bottom, 15.dp)
                    start.linkTo(parent.start)
                },
                color = MaterialTheme.chattyColors.textColor
            )
            Text(
                text = "手机号",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(phoneTitleRef) {
                    top.linkTo(ageTitleRef.bottom, 15.dp)
                    start.linkTo(parent.start)
                },
                color = MaterialTheme.chattyColors.textColor
            )
            Text(
                text = "电子邮箱",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(emailTitleRef) {
                    top.linkTo(phoneTitleRef.bottom, 15.dp)
                    start.linkTo(parent.start)
                },
                color = MaterialTheme.chattyColors.textColor
            )

            Text(
                text = user.gender ?: "未知",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(genderRef) {
                    top.linkTo(genderTitleRef.top)
                    start.linkTo(barrier, barrierDistance)
                },
                color = MaterialTheme.chattyColors.textColor
            )

            Text(
                text = user.age?.toString() ?: "未知",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(ageRef) {
                    top.linkTo(ageTitleRef.top)
                    start.linkTo(barrier, barrierDistance)
                },
                color = MaterialTheme.chattyColors.textColor
            )
            Text(
                text = user.phone ?: "未知",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(phoneRef) {
                    top.linkTo(phoneTitleRef.top)
                    start.linkTo(barrier, barrierDistance)
                },
                color = MaterialTheme.chattyColors.textColor
            )
            Text(
                text = user.email ?: "未知",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(emailRef) {
                    top.linkTo(emailTitleRef.top)
                    start.linkTo(barrier, barrierDistance)
                },
                color = MaterialTheme.chattyColors.textColor
            )
        }
    }
}

@Preview
@Composable
fun MoreFriendOptions() {
    var isFollowed by remember { mutableStateOf(false) }
    var isBlocked by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = "更多设置",
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.chattyColors.textColor
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp))
        HeightSpacer(value = 10.dp)
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "添加到置顶",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.chattyColors.textColor
            )
            Switch(
                checked = isFollowed,
                onCheckedChange = {
                    isFollowed = it
                }
            )
        }
        HeightSpacer(value = 5.dp)
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "加入到黑名单",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.chattyColors.textColor
            )
            Switch(
                checked = isBlocked,
                onCheckedChange = {
                    isBlocked = it
                }
            )
        }
    }
}