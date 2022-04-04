package com.chatty.compose.ui.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.ui.theme.chattyColors

@Composable
fun MyBottomNavigationBar(
    selectedScreen: Int,
    screens: List<Screens>,
    onClick: (targetIndex: Int) -> Unit
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.chattyColors.backgroundColor,
        modifier = Modifier.navigationBarsPadding()
    ) {
        screens.forEachIndexed { index, screen ->
            BottomNavigationItem(
                selected = selectedScreen == index,
                onClick = { onClick(index) },
                icon = {
                    Icon(
                        painterResource(screen.resId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.chattyColors.iconColor
                    )
                },
                label = { Text(screen.label, color = MaterialTheme.chattyColors.textColor) },
            )
        }
    }
}

data class Screens(
    val label: String,
    val resId: Int,
    val content: @Composable () -> Unit
)

object AppScreen {
    const val login = "login"
    const val main = "main"
    const val register = "register"
    const val splash = "splash"
    const val userProfile = "user_profile"
    const val strangerProfile = "stranger_profile"
    const val profileEdit = "profile_edit"
    const val addFriends = "add_friends"
    const val createPost = "create_post"
    const val qr_scan = "qr_scan"
    const val conversation = "conversation"
}
