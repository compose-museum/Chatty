package com.chatty.compose.ui.pages

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chatty.compose.R
import com.chatty.compose.common.AuthStatus
import com.chatty.compose.database.bean.User
import com.chatty.compose.ui.components.Avatar
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.viewmodel.AppScreen
import com.chatty.compose.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun Login(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val loginStatus by loginViewModel.loginStatus.collectAsState(initial = null)
    val savedUsers by loginViewModel.savedUsers.collectAsState(initial = listOf())

    val enableDropMenuButton by remember(savedUsers.size) { mutableStateOf(savedUsers.size > 1) }
    var openDropMenu by remember { mutableStateOf(false) }

    var selectedUser by remember(savedUsers) { mutableStateOf(if (savedUsers.isNotEmpty()) savedUsers[0] else null) }

    var username by remember(selectedUser) { mutableStateOf(selectedUser?.username) }
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
                    Image(painterResource(id = R.drawable.logo), null, modifier = Modifier.size(80.dp))
                    WidthSpacer(value = 12.dp)
                    Text(
                        text = "Ice",
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
                        value = username ?: "",
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
                            Avatar(selectedUser, modifier = Modifier.size(35.dp))
                        },
                        trailingIcon = {
                            if (enableDropMenuButton) {
                                IconButton(
                                    onClick = {
                                        keyboardController?.hide()
                                        openDropMenu = !openDropMenu
                                    }
                                ) {
                                    Icon(painterResource(id = R.drawable.expand), null)
                                }
                            }
                        }
                    )
                }
                DropdownMenu(
                    expanded = openDropMenu,
                    onDismissRequest = { openDropMenu = false }
                ) {
                    if (savedUsers.size > 1) {
                        SavedUserList(
                            list = savedUsers,
                            onClick = { user ->
                                selectedUser = user
                                openDropMenu = false
                            }
                        )
                    }
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
                         username?.let { loginViewModel.login(it, password) }
                     }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (loginStatus != AuthStatus.FALSE) Color(0xFF0079D3) else MaterialTheme.colors.error
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = !username.isNullOrEmpty()
            ) {
                when (loginStatus) {
                    AuthStatus.UNAUTH -> {
                        Icon(painterResource(R.drawable.login), null, tint = Color.White)
                        WidthSpacer(5.dp)
                        Text("登入", color = Color.White)
                    }
                    AuthStatus.AUTHING -> CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(30.dp),
                        strokeWidth = 4.dp
                    )
                    AuthStatus.FALSE -> {
                        Icon(painterResource(R.drawable.error), null, tint = Color.White)
                        WidthSpacer(5.dp)
                        Text("登入失败", color = Color.White)
                    }
                    AuthStatus.AUTHED -> {
                        Text("登入成功", color = Color.White)
                    }
                }
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
                            navController.navigate(AppScreen.register.route)
                        },
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    )
                )
            }
        }
    }
    LaunchedEffect(loginStatus) {
        if (loginStatus == AuthStatus.AUTHED) {
            navController.navigate(AppScreen.home.route) {
                popUpTo(AppScreen.login.route) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun SavedUserList(
    list: List<User>,
    onClick: (user: User) -> Unit
) {
    list.forEachIndexed { _, item ->
        DropdownMenuItem(
            onClick = { onClick(item) }
        ) {
            CenterRow  {
                Avatar(user = item, modifier = Modifier.size(35.dp))
                WidthSpacer(value = 8.dp)
                Text(
                    text = item.username,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}
