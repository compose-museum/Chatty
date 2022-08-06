package com.chatty.compose.screens.home

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.theme.LocalChattyColors
import com.chatty.compose.ui.theme.chattyColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val chattyColors = LocalChattyColors.current

    CenterAlignedTopAppBar(
        title = {
              Text("Chatty", color = MaterialTheme.chattyColors.textColor)
        },
        actions = {
            IconButton(
                onClick = { chattyColors.toggleTheme() }
            ) {
                Icon(
                    imageVector = if (chattyColors.isLight)Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                },
            ) {
                CircleShapeImage(size = 40.dp, painter = painterResource(id = R.drawable.ava4))
            }
        },
        modifier = Modifier.statusBarsPadding()
    )
}
