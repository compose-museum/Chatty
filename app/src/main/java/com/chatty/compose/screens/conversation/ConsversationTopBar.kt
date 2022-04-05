package com.chatty.compose.screens.conversation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatty.compose.ui.components.TopBar
import com.chatty.compose.ui.theme.chattyColors


@Preview
@Composable
fun ConversationTopBar(
    conversationName: String = "MyFriend",
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { }
) {
    TopBar(modifier = modifier,
        backgroundColor = MaterialTheme.chattyColors.backgroundColor,
        start = {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .clickable(onClick = onNavIconPressed)
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .height(24.dp),
                contentDescription = null
            )

        }, center = {
            Text(
                text = conversationName,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.chattyColors.textColor
            )
        })

}