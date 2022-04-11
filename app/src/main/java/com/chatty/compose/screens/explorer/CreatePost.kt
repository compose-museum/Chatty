package com.chatty.compose.screens.explorer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.components.TopBar
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController
import com.chatty.compose.ui.utils.hideIME
import kotlinx.coroutines.launch

@Composable
fun CreatePost() {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.chattyColors.backgroundColor)
    ) {
        CreatePostTopBar()
        Divider(Modifier.fillMaxWidth())
        Column {
            CenterRow(Modifier.padding(start = 14.dp, end = 14.dp, top = 14.dp)) {
                CircleShapeImage(size = 40.dp, painter = painterResource(id = R.drawable.ava4))
                WidthSpacer(value = 4.dp)
                Text("香辣鸡腿堡",Modifier.weight(1f) ,style = MaterialTheme.typography.h6, color = MaterialTheme.chattyColors.textColor)
                Text("字数：${text.length}", Modifier.alpha(0.5f), color = MaterialTheme.chattyColors.textColor)
            }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.chattyColors.textColor,
                ),
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .fillMaxSize(),
                placeholder = {
                    Text("最近发生了什么有意思的事情？", color = MaterialTheme.chattyColors.textColor, modifier = Modifier.alpha(0.5f))
                },
                textStyle = TextStyle(color = MaterialTheme.chattyColors.textColor)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreatePostTopBar() {

    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current
    val context = LocalContext.current

    TopBar(
        backgroundColor = MaterialTheme.chattyColors.backgroundColor,
        start = { 
            IconButton(onClick = {
                scope.launch {
                    context.hideIME()
                    navController.popBackStack()
                }
            }) {
                Icon(Icons.Rounded.ArrowBack, null, tint = MaterialTheme.chattyColors.iconColor)
            }
        },
        center = {
            Text("发表新鲜事", color = MaterialTheme.chattyColors.textColor)
        },
        end = {
            IconButton(onClick = {
                scope.launch {
                    context.hideIME()
                    navController.popBackStack()
                }
            }) {
                Icon(Icons.Rounded.Done, null, tint = MaterialTheme.chattyColors.iconColor)
            }
        },
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    )
}
