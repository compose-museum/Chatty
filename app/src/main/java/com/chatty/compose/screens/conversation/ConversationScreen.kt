package com.chatty.compose.screens.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.chatty.compose.R
import com.chatty.compose.bean.UserProfileData
import com.chatty.compose.fetchUserInfoById
import com.chatty.compose.screens.conversation.mock.initialMessages
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController
import kotlinx.coroutines.launch


val LocalConversationUser = compositionLocalOf<UserProfileData> {
    error("CompositionLocal LocalConversationUser not found")
}


@Preview
@Composable
fun ConversationScreen(
    modifier: Modifier = Modifier,
    uiState: ConversationUiState = ConversationUiState(
        initialMessages = initialMessages,
        conversationUserId = "1024"
    )
) {

    val timeNow = stringResource(id = R.string.now)
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {

            val navController =
                if (LocalInspectionMode.current) rememberNavController()
                else LocalNavController.current

            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.chattyColors.backgroundColor)
            ) {

                CompositionLocalProvider(LocalConversationUser provides fetchUserInfoById(uiState.conversationUserId)) {
                    Messages(
                        messages = uiState.messages,
                        navigateToProfile = { navController.navigate("${AppScreen.userProfile}/${uiState.conversationUserId}") },
                        modifier = Modifier.weight(1f),
                        scrollState = scrollState
                    )
                }


                UserInput(
                    onMessageSent = { content ->
                        uiState.addMessage(
                            Message(true, content, timeNow)
                        )
                    },
                    resetScroll = {
                        scope.launch {
                            scrollState.scrollToItem(0)
                        }
                    },
                    // Use navigationBarsPadding() imePadding() and , to move the input panel above both the
                    // navigation bar, and on-screen keyboard (IME)
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding(),
                )
            }

            ConversationTopBar(
                conversationName = fetchUserInfoById(uiState.conversationUserId).nickname,
                onNavIconPressed = { navController.popBackStack() },
                modifier = Modifier,
            )
        }
    }
}

