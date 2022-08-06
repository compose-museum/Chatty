package com.chatty.compose.screens.explorer

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.utils.LocalNavController
import com.chatty.compose.ui.utils.hideIME
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePost() {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "发表新鲜事")
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                context.hideIME()
                                navController.popBackStack()
                            }
                        },
                        enabled = text.isNotEmpty()
                    ) {
                        Icon(Icons.Rounded.Done, null)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                context.hideIME()
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(Icons.Rounded.ArrowBack, null)
                    }
                },
                modifier = Modifier.statusBarsPadding()
            )
        },
        content = { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                CenterRow(Modifier.padding(start = 14.dp, end = 14.dp, top = 14.dp)) {
                    CircleShapeImage(size = 40.dp, painter = painterResource(id = R.drawable.ava4))
                    WidthSpacer(value = 4.dp)
                    Text("香辣鸡腿堡",Modifier.weight(1f) ,style = MaterialTheme.typography.titleMedium)
                    Text("字数：${text.length}", Modifier.alpha(0.5f))
                }
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                        .fillMaxSize(),
                    placeholder = {
                        Text("最近发生了什么有意思的事情？", modifier = Modifier.alpha(0.5f))
                    }
                )
            }
        }
    )
}
