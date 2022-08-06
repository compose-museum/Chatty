package com.chatty.compose.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.R

@Composable
fun MyBottomNavigationBar(
    selectedScreen: Int,
    onClick: (targetIndex: Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding()
    ) {
        BottomScreen.values().forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = selectedScreen == index,
                onClick = { onClick(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedScreen == index) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(id = screen.label)) }
            )
        }
    }
}

enum class BottomScreen(
    @StringRes val label: Int,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    Message(R.string.message, Icons.Outlined.Chat, Icons.Filled.Chat),
    Contract(R.string.contract, Icons.Outlined.Group, Icons.Filled.Group),
    Explore(R.string.explore, Icons.Outlined.Explore, Icons.Filled.Explore)
}

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
