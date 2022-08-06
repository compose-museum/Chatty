package com.chatty.compose.screens.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.chatty.compose.AppTheme
import com.chatty.compose.R
import com.chatty.compose.bean.UserProfileData
import com.chatty.compose.screens.home.mock.friends
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController


@Composable
fun PersonalProfile() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .paint(
                    painterResource(id = R.drawable.google_bg),
                    contentScale = ContentScale.FillBounds
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            PersonalProfileHeader()
        }
        HeightSpacer(value = 10.dp)
        PersonalProfileDetail()
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomSettingIcons()
        }
    }
}


fun getCurrentLoginUserProfile(): UserProfileData {
    return friends[0]
}

@Composable
fun PersonalProfileHeader() {
    val currentUser = getCurrentLoginUserProfile()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
    ) {
        val (portraitImageRef, usernameTextRef, desTextRef) = remember { createRefs() }
        Image(
            painter = painterResource(id = currentUser.avatarRes),
            contentDescription = "portrait",
            modifier = Modifier
                .constrainAs(portraitImageRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .clip(CircleShape)
        )
        Text(
            text = currentUser.nickname,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Left,
            color = Color.White,
            modifier = Modifier
                .constrainAs(usernameTextRef) {
                    top.linkTo(portraitImageRef.top, 5.dp)
                    start.linkTo(portraitImageRef.end, 10.dp)
                    width = Dimension.preferredWrapContent
                }
        )
        Text(
            text = currentUser.motto,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(desTextRef) {
                    top.linkTo(usernameTextRef.bottom, 10.dp)
                    start.linkTo(portraitImageRef.end, 10.dp)
                    end.linkTo(parent.end)
                    width = Dimension.preferredWrapContent
                }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalProfileDetail() {
    var currentUser = getCurrentLoginUserProfile()
    val navController = LocalNavController.current
    val chattyColors = MaterialTheme.chattyColors
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
    ) {
        CenterRow {
            Text(
                text = "个人信息",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { chattyColors.toggleTheme() }
            ) {
                Icon(
                    imageVector = if (chattyColors.isLight)Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Spacer(Modifier.padding(vertical = 12.dp))
        PersonalProfileItem.values().forEach { item ->
            NavigationDrawerItem(
                label = {
                    Text(item.label, style = MaterialTheme.typography.titleMedium)
                },
                selected = true,
                badge = {
                    item.badge?.let {
                        Text(it)
                    }
                },
                icon = {
                    Icon(item.icon, null)
                },
                onClick = {
                    when (item) {
                        PersonalProfileItem.SEX -> navController.navigate("${AppScreen.profileEdit}/gender")
                        PersonalProfileItem.AGE -> navController.navigate("${AppScreen.profileEdit}/age")
                        PersonalProfileItem.PHONE -> navController.navigate("${AppScreen.profileEdit}/phone")
                        PersonalProfileItem.EMAIL -> navController.navigate("${AppScreen.profileEdit}/email")
                        PersonalProfileItem.QRCODE -> navController.navigate("${AppScreen.profileEdit}/qrcode")
                    }
                }
            )
            HeightSpacer(value = 8.dp)
        }
    }
}

enum class PersonalProfileItem(
    val label: String,
    val badge: String?,
    val icon: ImageVector
) {
    SEX("性别", "男", Icons.Rounded.Male),
    AGE("年龄", "19", Icons.Rounded.Circle),
    PHONE("手机号", "未知", Icons.Rounded.Call),
    EMAIL("电子邮箱", "未知", Icons.Rounded.Mail),
    QRCODE("二维码", null, Icons.Rounded.QrCode)
}

@Composable
fun BottomSettingIcons() {
    val navController = LocalNavController.current
    CenterRow(
        Modifier
            .fillMaxWidth()
            .padding(18.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.clickable(
                onClick = {

                },
                indication = null,
                interactionSource = MutableInteractionSource()
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Rounded.Settings, contentDescription = null)
            Text(text = "设置", fontSize = 15.sp)
        }
        Column(
            modifier = Modifier.clickable(
                onClick = {
                      navController.navigate(AppScreen.login) {
                          popUpTo(AppScreen.main) { inclusive = true }
                      }
                },
                indication = null,
                interactionSource = MutableInteractionSource()
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Rounded.Logout, contentDescription = null)
            Text(text = "注销", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}