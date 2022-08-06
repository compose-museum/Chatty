package com.chatty.compose.screens.contracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatty.compose.R
import com.chatty.compose.bean.UserProfileData
import com.chatty.compose.screens.home.mock.friends
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.TopBar
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController


@Preview
@Composable
fun StrangerProfileDemo() {
    StrangerProfile(friends[0], "用户名搜索")
}

@Composable
fun StrangerProfile(user: UserProfileData, formSource: String) {
    var confirmDialogState by remember { mutableStateOf(false) }
    val naviController = LocalNavController.current
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.chattyColors.backgroundColor)) {
        Column(modifier = Modifier.fillMaxSize()) {
            StrangerProfileTopBar()
            Column(modifier = Modifier.background(MaterialTheme.chattyColors.backgroundColor)) {
                StrangerProfileInfo(user)
                StrangerMoreInfo(formSource)
            }
            Button(onClick = {
                confirmDialogState = true
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = "添加联系人",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
        if (confirmDialogState) {
            AlertDialog(
                onDismissRequest = {
                    confirmDialogState = false
                },
                title = {
                    Text(
                        text = "添加联系人",
                        fontWeight = FontWeight.W700,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                text = {
                    Text(
                        text = "确定发送好友申请吗？",
                        fontSize = 16.sp
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            confirmDialogState = false
                            naviController.popBackStack(AppScreen.main, inclusive = false)
                        },
                    ) {
                        Text(
                            "确认",
                            fontWeight = FontWeight.W700,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            confirmDialogState = false
                        }
                    ) {
                        Text(
                            "取消",
                            fontWeight = FontWeight.W700,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun StrangerProfileInfo(
    user: UserProfileData
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        color = MaterialTheme.chattyColors.backgroundColor
    ) {
        CenterRow(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            Image(
                painter = painterResource(id = user.avatarRes),
                contentDescription = "avatar",
                modifier = Modifier.size(60.dp)
            )
            Spacer(Modifier.padding(horizontal = 10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row{
                    Text(
                        text = user.nickname,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.chattyColors.textColor
                    )
                    if (user.gender != null) {
                        WidthSpacer(value = 3.dp)
                        Icon(
                            painter = painterResource(id = if (user.gender == "男") R.drawable.male else R.drawable.female),
                            contentDescription = "gender",
                            tint = if (user.gender == "男") Color.Blue else Color(0xffd93a7d)
                        )
                    }
                }
                Spacer(Modifier.padding(vertical = 3.dp))
                Text(
                    text = user.motto,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.chattyColors.textColor
                )
            }
        }
    }
}


@Composable
fun StrangerProfileTopBar() {
    var naviController = LocalNavController.current
    TopBar(
        start = {
            IconButton(onClick = {
                naviController.popBackStack()
            }) {
                Icon(Icons.Rounded.ArrowBack, null, tint = MaterialTheme.chattyColors.iconColor)
            }
        },
        center = {
            Text("联系人", fontWeight = FontWeight.Bold, color = MaterialTheme.chattyColors.textColor)
        },
        backgroundColor = MaterialTheme.chattyColors.backgroundColor,
        elevation = 0.dp
    )
}

@Composable
fun StrangerMoreInfo(formSource: String) {
    Column {
        StrangerMoreInfoRowItem("搜索来源", formSource)
    }
}
@Composable
fun StrangerMoreInfoRowItem(label: String, content: String = "") {
    Box(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 5.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.chattyColors.textColor
            )
            WidthSpacer(value = 5.dp)
            Text(
                text = content,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.chattyColors.textColor
            )
        }
    }
}