package com.chatty.compose.screens.chatty

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.chatty.compose.R
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.components.WidthSpacer

@Preview
@Composable
fun PersonalProfile() {
    Scaffold(
        bottomBar = {
            BottomSettingIcons()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
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
        }
    }
}



@Composable
fun PersonalProfileHeader() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
    ) {
        val (portraitImageRef, usernameTextRef, desTextRef) = remember { createRefs() }
        Image(
            painter = painterResource(id = R.drawable.ava2),
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
            text = "Zinger Burger",
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
            text = "To be or not to be, that's a question",
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PersonalProfileDetail() {
    var genderEditable by remember { mutableStateOf(false) }
    var ageEditable by remember { mutableStateOf(false) }
    var phoneEditable by remember { mutableStateOf(false) }
    var emailEditable by remember { mutableStateOf(false) }


    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            text = "个人信息",
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.padding(start = 20.dp)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            ProfileDetailRowItem(label = "性别", content = "男") {

            }
            ProfileDetailRowItem(label = "年龄", content = "20") {

            }
            ProfileDetailRowItem(label = "手机号", content = "10086") {

            }
            ProfileDetailRowItem(label = "电子邮箱", content = "zinger_burger@gmail.com") {

            }
            ProfileDetailRowItem(label = "二维码", isQrCode = true) {

            }
        }
    }
}


@Composable
fun ProfileDetailRowItem(label: String, content: String = "", isQrCode: Boolean = false, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }
            .padding(vertical = 30.dp, horizontal = 20.dp),
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
                fontWeight = FontWeight.Bold
            )
            Row {
                if (isQrCode) {
                    Icon(painter = painterResource(id = R.drawable.qr_code), contentDescription = label)
                } else {
                    Text(
                        text = content,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                }
                Icon(painter = painterResource(id = R.drawable.expand_right), contentDescription = label)
            }
        }
    }
}

@Composable
fun BottomSettingIcons() {
    CenterRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            IconButton(
                onClick = {}
            ) {
                Column(
                    modifier = Modifier.size(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(painter = painterResource(id = R.drawable.settings), contentDescription = null)
                    HeightSpacer(value = 4.dp)
                    Text(text = "设置", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
            WidthSpacer(value = 10.dp)
            IconButton(
                onClick = {}
            ) {
                Column(
                    modifier = Modifier.size(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(painter = painterResource(id = R.drawable.dark_mode), contentDescription = null)
                    HeightSpacer(value = 4.dp)
                    Text(text = "主题", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        IconButton(
            onClick = { }
        ) {
            Column(
                modifier = Modifier.size(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.logout), contentDescription = null)
                HeightSpacer(value = 4.dp)
                Text(text = "注销", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}