package com.chatty.compose.ui.pages

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.chatty.compose.R
import com.chatty.compose.common.AuthStatus
import com.chatty.compose.common.NameStatus
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.theme.ok
import com.chatty.compose.viewmodel.AppScreen
import com.chatty.compose.viewmodel.RegisterViewModel

@ExperimentalComposeUiApi
@Composable
fun Register(
    navController: NavHostController,
    registerViewModel: RegisterViewModel
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val nameStatus by remember { registerViewModel.nameStatus }
    val registerStatus by remember { registerViewModel.registerStatus }

    var focusedTextField by remember { mutableStateOf(-1) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var passwordHidden by remember { mutableStateOf(true) }

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                registerViewModel.createLocalAvatar(it, context)
            }
        }

    Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color(0xFFF8F8F8))
    ) {
        Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 35.dp, vertical = 48.dp)
        ) {
            Text(
                text = "注册账号",
                style = MaterialTheme.typography.h4,
                color = Color(0xFF0E4A86)
            )
            HeightSpacer(value = 15.dp)
            Box {
                Box(
                    modifier = Modifier
                      .size(80.dp)
                      .border(2.dp, Color(0xFF0079D3), CircleShape)
                      .background(color = Color.Gray, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Transparent
                    ) {
                        Image(
                            painter = if (imageUri == null) painterResource(id = R.drawable.person) else rememberImagePainter(
                                imageUri
                            ),
                            contentDescription = null,
                            contentScale = if (imageUri == null) ContentScale.Fit else ContentScale.Crop,
                            modifier = Modifier
                                .clickable {
                                    launcher.launch("image/*")
                                }
                        )
                    }
                }
                Image(
                    painterResource(id = R.drawable.camera), null,
                    modifier = Modifier
                      .size(20.dp)
                      .align(Alignment.BottomEnd)
                )
            }
            HeightSpacer(value = 12.dp)
            Surface(
                elevation = 5.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                TextField(
                    value = username,
                    onValueChange = {
                        username = it
                        if (it.isNotEmpty())
                            registerViewModel.checkName(username)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor =
                        if (nameStatus == NameStatus.OK) ok
                        else Color.Transparent,
                        focusedLabelColor = Color(0xFF575C62),
                        cursorColor = Color(0xFF575C62),
                        errorCursorColor = MaterialTheme.colors.error,
                    ),
                    label = {
                        Text("用户名")
                    },
                    modifier = Modifier
                      .fillMaxWidth()
                      .onFocusChanged {
                        if (it.isFocused) {
                          registerViewModel.checkName(username)
                          focusedTextField = 1
                        }
                      },
                    shape = RoundedCornerShape(8.dp),
                    isError = (nameStatus != NameStatus.OK && focusedTextField == 1)
                            || (username.isEmpty() && focusedTextField == 1),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }
            if (focusedTextField == 1
                || (focusedTextField != 1 && username.isNotEmpty())
            ) {
                HeightSpacer(value = 4.dp)
                Text(
                    text = if (username.isEmpty()) {
                        "用户名无法为空"
                    } else {
                        when (nameStatus) {
                            NameStatus.INVALID -> "你无法使用该用户名"
                            NameStatus.UNAVAILABLE -> "请稍后重试"
                            NameStatus.OK -> "恭喜，你可以使用此用户名"
                            NameStatus.MALFORMED -> "用户名格式有误"
                        }
                    },
                    color =
                    if (nameStatus != NameStatus.OK || username.isEmpty()) MaterialTheme.colors.error
                    else ok
                )
            }
            HeightSpacer(value = 12.dp)
            Surface(
                elevation = 5.dp,
                shape = RoundedCornerShape(8.dp)
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
                        focusedLabelColor = Color(0xFF575C62),
                        cursorColor = Color(0xFF575C62),
                    ),
                    label = {
                        Text("密码")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordHidden = !passwordHidden
                            }
                        ) {
                            Icon(painterResource(id = R.drawable.visibility), null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }
            HeightSpacer(value = 12.dp)
            Surface(
                elevation = 5.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                TextField(
                    value = repeatPassword,
                    onValueChange = {
                        repeatPassword = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color(0xFF575C62),
                        cursorColor = Color(0xFF575C62),
                    ),
                    label = {
                        Text("确认密码")
                    },
                    modifier = Modifier
                      .fillMaxWidth()
                      .onFocusChanged {
                        if (it.isFocused) {
                          focusedTextField = 3
                        }
                      },
                    shape = RoundedCornerShape(8.dp),
                    trailingIcon = {
                        if (password == repeatPassword && repeatPassword.isNotEmpty()) {
                            Icon(Icons.Filled.Check, null, tint = ok)
                        }
                    },
                    visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { keyboardController?.hide() })
                )
            }
            if (password != repeatPassword && focusedTextField == 3) {
                HeightSpacer(value = 4.dp)
                Text(
                    text = "输入的密码不一致",
                    color = MaterialTheme.colors.error
                )
            }
            HeightSpacer(value = 22.dp)
            Button(
                onClick = {
                    registerViewModel.register(username, password, context)
                },
                enabled = (password == repeatPassword && password.isNotEmpty())
                        && (username.isNotEmpty() && nameStatus == NameStatus.OK),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =
                    if (registerStatus == AuthStatus.FALSE)
                        MaterialTheme.colors.error
                    else Color(0xFF0079D3)
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(48.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (registerStatus != AuthStatus.AUTHING) {
                    Text(
                        text = when (registerStatus) {
                            AuthStatus.UNAUTH -> "注册账号"
                            AuthStatus.FALSE -> "注册失败"
                            AuthStatus.AUTHED -> "注册成功"
                            else -> ""
                        },
                        color = Color.White
                    )
                } else {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(30.dp),
                        strokeWidth = 4.dp
                    )
                }
            }
        }
    }
    LaunchedEffect(registerStatus) {
        if (registerStatus == AuthStatus.AUTHED) {
            navController.navigate(AppScreen.home.route) {
                popUpTo(AppScreen.register.route) {
                    inclusive = true
                }
                popUpTo(AppScreen.login.route) {
                    inclusive = true
                }
            }
            registerViewModel.registerStatus.value = AuthStatus.UNAUTH
        }
    }
    BackHandler {
        navController.popBackStack()
        registerViewModel.clearTempUser(context)
    }
}
