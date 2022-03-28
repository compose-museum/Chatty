package com.chatty.compose.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.utils.LocalNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login() {
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var openDropMenu by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(top = 48.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CenterRow {
                    //Image(painterResource(id = R), null, modifier = Modifier.size(80.dp))
                    WidthSpacer(value = 12.dp)
                    Text(
                        text = "Chatty",
                        style = MaterialTheme.typography.h1,
                        color = Color(0xFF0E4A86),
                        fontFamily = FontFamily.Cursive
                    )
                }
            }
            HeightSpacer(value = 20.dp)
            Column {
                Surface(
                    elevation = 10.dp,
                    shape =  RoundedCornerShape(8.dp)
                ) {
                    TextField(
                        value = username,
                        onValueChange = {
                            username = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedLabelColor = Color(0xFF575C62)
                        ),
                        label = {
                            Text("用户名")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape =  RoundedCornerShape(8.dp),
                        leadingIcon = {
                            //Avatar(selectedUser, modifier = Modifier.size(35.dp))
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    keyboardController?.hide()
                                    openDropMenu = !openDropMenu
                                }
                            ) {
                                Icon(painterResource(R.drawable.expand), null)
                            }
                        }
                    )
                }
            }
            HeightSpacer(value = 10.dp)
            Surface(
                elevation = 10.dp,
                shape =  RoundedCornerShape(8.dp)
            ) {
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color(0xFF575C62)
                    ),
                    label = {
                        Text("密码")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape =  RoundedCornerShape(8.dp),
                    visualTransformation = if(passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordHidden = !passwordHidden
                            }
                        ) {
                            Icon(painterResource(id = R.drawable.visibility), null)
                        }
                    }
                )
            }
            HeightSpacer(value = 20.dp)
            Button(
                onClick = {
                    scope.launch {
                        navController.navigate(AppScreen.main) {
                            popUpTo(AppScreen.login) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(painterResource(R.drawable.login), null, tint = Color.White)
                WidthSpacer(5.dp)
                Text("登入", color = Color.White)
            }
            HeightSpacer(value = 15.dp)
            CenterRow {
                Text(
                    text = "忘记密码？",
                    style = MaterialTheme.typography.button
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "注册账号",
                    style = MaterialTheme.typography.button,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable(
                        onClick = {
                            navController.navigate(AppScreen.register)
                        },
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    )
                )
            }
        }
    }
}
