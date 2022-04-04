package com.chatty.compose.screens.chatty

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatty.compose.R
import com.chatty.compose.screens.chatty.mock.displayMessages
import com.chatty.compose.screens.chatty.mock.recentMessages
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.components.TopBar
import com.chatty.compose.ui.theme.LocalChattyColors
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalScaffoldState
import com.chatty.compose.ui.utils.hideIME
import kotlinx.coroutines.launch
import java.util.*

@Preview
@Composable
fun ChattyTopBar() {
    val scaffoldState = LocalScaffoldState.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    var isSearching by remember { mutableStateOf(false) }
    var searchContent by remember { mutableStateOf("") }

    TopBar(
        backgroundColor = MaterialTheme.chattyColors.backgroundColor,
        start =  {
            IconButton(
                onClick = {
                    if (!isSearching) {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                },
            ) {
                CircleShapeImage(size = 32.dp, painter = painterResource(id = R.drawable.ava4))
            }
        },
        center = {
            if (isSearching) {
                BasicTextField(
                    value = searchContent,
                    onValueChange = {
                        searchContent = it.lowercase(Locale.getDefault())
                        displayMessages = recentMessages.filter { result ->
                            result.userProfile.nickname.lowercase(Locale.getDefault()).contains(searchContent)
                        }.toMutableList()
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    modifier = Modifier
                        .width(300.dp)
                        .padding(horizontal = 4.dp)
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.chattyColors.textColor)
                ) { innerText ->
                    CenterRow(Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerText()
                        }
                    }
                }
            } else {
                Text("Chatty", color = MaterialTheme.chattyColors.textColor)
            }
        },
        end = {
            if (isSearching) {
                IconButton(
                    onClick = {
                        isSearching = false
                        displayMessages = recentMessages
                        searchContent = ""
                    },
                ) {
                    Icon(Icons.Filled.Close, null, tint = MaterialTheme.chattyColors.iconColor)
                }
            } else {
                IconButton(
                    onClick = {
                        isSearching = true
                    }
                ) {
                    Icon(Icons.Rounded.Search, null, tint = MaterialTheme.chattyColors.iconColor)
                }
            }
        }
    )
    LaunchedEffect(isSearching) {
        if (isSearching) {
            focusRequester.requestFocus()
        } else {
            context.hideIME()
        }
    }
}
