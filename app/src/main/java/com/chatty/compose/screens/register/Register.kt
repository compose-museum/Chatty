package com.chatty.compose.screens.register

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.chatty.compose.R
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.theme.green
import com.chatty.compose.ui.utils.LocalNavController
import com.chatty.compose.ui.utils.popUpAllBackStackEntry

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Register() {

    val keyboardController = LocalSoftwareKeyboardController.current
    val navController = LocalNavController.current

    var focusedTextField by rememberSaveable { mutableStateOf(-1) }

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 35.dp, vertical = 48.dp)
        ) {
            Text(
                text = "注册账号",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
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
                            painter = imageUri?.let { rememberAsyncImagePainter(imageUri) }
                                ?:run { painterResource(id = R.drawable.ava1) },
                            contentDescription = null,
                            contentScale = if (imageUri == null) ContentScale.Fit else ContentScale.Crop,
                            modifier = Modifier.clickable { launcher.launch("image/*") }
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
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent
                ),
                label = {
                    Text("用户名")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            focusedTextField = 1
                        }
                    },
                shape =  RoundedCornerShape(8.dp),
                isError = (username.isEmpty() && focusedTextField == 1),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            if (focusedTextField == 1 || username.isNotEmpty()) {
                HeightSpacer(value = 4.dp)
                Text(
                    text = if (username.isEmpty()) "用户名无法为空" else "恭喜，你可以使用此用户名",
                    color = if (username.isNotEmpty()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error
                )
            }
            HeightSpacer(value = 12.dp)
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent
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
                        Icon(painterResource(id = R.drawable.visibility), null, tint = MaterialTheme.chattyColors.iconColor)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            HeightSpacer(value = 12.dp)
            OutlinedTextField(
                value = repeatPassword,
                onValueChange = {
                    repeatPassword = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent
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
                shape =  RoundedCornerShape(8.dp),
                trailingIcon = {
                    if (password == repeatPassword && repeatPassword.isNotEmpty()) {
                        Icon(Icons.Filled.Check, null, tint = green)
                    }
                },
                visualTransformation = if(passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = { keyboardController?.hide() })
            )
            if (password != repeatPassword && focusedTextField == 3) {
                HeightSpacer(value = 4.dp)
                Text(
                    text = "输入的密码不一致",
                    color = MaterialTheme.colorScheme.error
                )
            }
            HeightSpacer(value = 22.dp)
            Button(
                onClick = {
                    navController.navigate(AppScreen.main) {
                        popUpAllBackStackEntry(navController)
                    }
                },
                enabled = (password == repeatPassword && password.isNotEmpty()),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0079D3)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("完成注册", color = Color.White)
            }
        }
    }

    BackHandler {
        navController.popBackStack()
    }
}
