package com.chatty.compose.screens.explorer

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.components.WidthSpacer

@Composable
fun CreatePost() {
    var text by remember { mutableStateOf("") }
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        CreatePostTopBar()
        Divider(Modifier.fillMaxWidth())
        Column {
            CenterRow(Modifier.padding(14.dp)) {
                CircleShapeImage(size = 40.dp, painter = painterResource(id = R.drawable.ava4))
                WidthSpacer(value = 4.dp)
                Text("香辣鸡腿堡",Modifier.weight(1f) ,style = MaterialTheme.typography.h6)
                Text("字数：${text.length}", Modifier.alpha(0.5f))
            }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.height(300.dp),
                placeholder = {
                    Text("最近发生了什么有意思的事情？")
                }
            )
        }
    }
}

@Composable
fun CreatePostTopBar() {
    TopAppBar(
        contentPadding = WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues(),
        backgroundColor = Color.White
    ) {
        CenterRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.ArrowBack, null)
            }
            Text("发表新鲜事")
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.Done, null)
            }
        }
    }
}
