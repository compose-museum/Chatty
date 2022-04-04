package com.chatty.compose.screens.chatty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chatty.compose.bean.UserProfileData
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.components.NumberChips
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController
import kotlin.random.Random

@Composable
fun FriendMessageItem(
    userProfileData: UserProfileData,
    lastMsg: String,
    unreadCount: Int = 0
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            },
        color = MaterialTheme.chattyColors.backgroundColor
    ) {
        val navController = LocalNavController.current
        CenterRow(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                .clickable {
                    navController.navigate("${AppScreen.conversation}/${userProfileData.uid}")
                }
        ) {
            CircleShapeImage(60.dp, painter = painterResource(id = userProfileData.avatarRes))
            Spacer(Modifier.padding(horizontal = 10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userProfileData.nickname,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.chattyColors.textColor
                )
                Spacer(Modifier.padding(vertical = 3.dp))
                Text(
                    text = lastMsg,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.chattyColors.textColor
                )
            }
            WidthSpacer(4.dp)
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text("${Random.nextInt(0, 24)}:${Random.nextInt(0, 60)}", color = MaterialTheme.chattyColors.textColor)
                Spacer(Modifier.padding(vertical = 3.dp))
                NumberChips(unreadCount)
            }
        }
    }
}
