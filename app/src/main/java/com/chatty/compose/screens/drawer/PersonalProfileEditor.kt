package com.chatty.compose.screens.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatty.compose.R
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.components.TopBar
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.theme.green
import com.chatty.compose.ui.utils.LocalNavController


@Preview
@Composable
fun Demo() {
    PersonalProfileEditor("设置性别", true, false)
}

@Composable
fun PersonalProfileEditor(title: String, isGender: Boolean = false, isQRCode: Boolean) {
    val navController = LocalNavController.current
    Column {
        TopBar(
            start = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Rounded.ArrowBack, null, tint = MaterialTheme.chattyColors.iconColor)
                }
            },
            center =  {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 20.dp)
                )
            },
            end =  {
                if (!isQRCode) {
                    Button(
                        onClick = { navController.navigate(AppScreen.main) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = green
                        ),
                    ) {
                        Text(text = "完成", color = Color.White)
                    } 
                }
            },
            backgroundColor = Color.White
        )
        when {
            isGender -> GenderSelector()
            isQRCode -> QRCodeDisplay()
            else -> ProfileInputField()
        }
    }
}

@Composable
fun ProfileInputField() {
    var inputText by remember {
        mutableStateOf("")
    }
    var focusRequester = remember {
        FocusRequester()
    }
    OutlinedTextField(
        value = inputText,
        onValueChange = {
            inputText = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .focusRequester(focusRequester),
        maxLines = 1,
        textStyle = TextStyle(
            fontSize = 20.sp
        ),
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun QRCodeDisplay() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = R.drawable.qrcode), contentDescription = "qr_code")
            HeightSpacer(value = 5.dp)
            Text(text = "使用扫一扫功能添加我")
        }
    }
}

@Composable
fun GenderSelector() {
    var selectMale by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    selectMale = true
                }
                .padding(horizontal = 20.dp)
                .height(80.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.male),
                    contentDescription = "male",
                    tint = Color.Blue
                )
                WidthSpacer(value = 3.dp)
                Text(
                    text = "男",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
            if (selectMale) {
                Icon(
                    painter = painterResource(id = R.drawable.correct),
                    contentDescription = "correct",
                    tint = green
                )
            }
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    selectMale = false
                }
                .padding(horizontal = 20.dp)
                .height(80.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.female),
                    contentDescription = "male",
                    tint = Color(0xffd93a7d)
                )
                WidthSpacer(value = 3.dp)
                Text(
                    text = "女",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
            if (!selectMale) {
                Icon(
                    painter = painterResource(id = R.drawable.correct),
                    contentDescription = "correct",
                    tint = green
                )
            }
        }
    }
}