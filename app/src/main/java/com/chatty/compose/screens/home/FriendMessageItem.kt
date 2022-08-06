package com.chatty.compose.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chatty.compose.bean.UserProfileData
import com.chatty.compose.ui.components.*
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController
import kotlin.random.Random

@Composable
fun FriendMessageItem(
    userProfileData: UserProfileData,
    lastMsg: String,
    unreadCount: Int = 0
) {
    val navController = LocalNavController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("${AppScreen.conversation}/${userProfileData.uid}")
            },
        color = MaterialTheme.chattyColors.backgroundColor
    ) {
        CenterRow(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
        ) {
            CircleShapeImage(60.dp, painter = painterResource(id = userProfileData.avatarRes))
            Spacer(Modifier.padding(horizontal = 10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userProfileData.nickname,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.chattyColors.textColor
                )
                Spacer(Modifier.padding(vertical = 3.dp))
                Text(
                    text = lastMsg,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.chattyColors.textColor
                )
            }
            WidthSpacer(4.dp)
            val randomTime = remember {
              "${Random.nextInt(0, 24)}:${Random.nextInt(10, 60)}"
            }
            if (unreadCount > 0) {
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(randomTime, color = MaterialTheme.chattyColors.textColor.copy(0.5f))
                        Spacer(Modifier.padding(vertical = 3.dp))
                        NumberChips(unreadCount)
                    }
                }
            }
        }
    }
}
