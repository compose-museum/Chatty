package com.chatty.compose.screens.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.theme.chattyColors


@Composable
fun Messages(
    messages: List<Message>,
    navigateToProfile: (String) -> Unit,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val conversationUser = LocalConversationUser.current

    Box(modifier = modifier) {

        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            // Add content padding so that the content can be scrolled (y-axis)
            // below the status bar + app bar
            // TODO: Get height from somewhere
            contentPadding =
            WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
            modifier = Modifier
                .fillMaxSize()
        ) {
            for (index in messages.indices) {
                val preTime = messages.getOrNull(index - 1)?.timestamp
                val nextTime = messages.getOrNull(index + 1)?.timestamp
                val content = messages[index]
                val isFirstMessageByTime = preTime != content.timestamp
                val isLastMessageByTime = nextTime != content.timestamp

                // Hardcode day dividers for simplicity
                if (index == messages.size - 1) {
                    item {
                        DayHeader("8月20日")
                    }
                } else if (index == 2) {
                    item {
                        DayHeader("今天")
                    }
                }

                item {
                    Message(
                        onAuthorClick = { navigateToProfile("${AppScreen.conversation}/${conversationUser.uid}") },
                        msg = content,
                        isUserMe = content.isUserMe,
                        isFirstMessageByAuthor = isFirstMessageByTime,
                        isLastMessageByAuthor = isLastMessageByTime
                    )
                }
            }
        }

    }
}

@Composable
fun Message(
    onAuthorClick: () -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
) {
    val borderColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }

    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Row(modifier = spaceBetweenAuthors) {
        if (isLastMessageByAuthor) {
            // Avatar
            Image(
                modifier = Modifier
                    .clickable(onClick = onAuthorClick)
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                painter = painterResource(id = if (isUserMe) R.drawable.ava2 else LocalConversationUser.current.avatarRes),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        } else {
            // Space under avatar
            Spacer(modifier = Modifier.width(74.dp))
        }
        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByTime = isFirstMessageByAuthor,
            isLastMessageByTime = isLastMessageByAuthor,
            authorClicked = onAuthorClick,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        )
    }
}


@Composable
fun AuthorAndTextMessage(
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByTime: Boolean,
    isLastMessageByTime: Boolean,
    authorClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (isLastMessageByTime) {
            AuthorNameTimestamp(msg)
        }
        ChatItemBubble(msg, isUserMe)
        if (isFirstMessageByTime) {
            // Last bubble before next author
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorNameTimestamp(msg: Message) {
    // Combine author and timestamp for a11y.
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = if (msg.isUserMe) stringResource(id = R.string.author_me) else LocalConversationUser.current.nickname,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp), // Space to 1st bubble
            color = MaterialTheme.chattyColors.textColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = msg.timestamp,
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.chattyColors.conversationHintText),
            modifier = Modifier.alignBy(LastBaseline),
        )
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)


@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .wrapContentHeight()
    ) {
        DayHeaderLine()
        Text(
            text = dayString,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.chattyColors.disabledContent
        )
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    // TODO (M3): No Divider, replace when available
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.chattyColors.disabledContent
    )
}

@Composable
fun ChatItemBubble(
    message: Message,
    isUserMe: Boolean
) {

    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.chattyColors.conversationBubbleBgMe
    } else {
        MaterialTheme.chattyColors.conversationBubbleBg
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape,
            shadowElevation = ConvasationBublblElevation
        ) {
            ClickableMessage(
                message = message,
                isUserMe = isUserMe,
            )
        }

        message.image?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = backgroundBubbleColor,
                shape = ChatBubbleShape,
                shadowElevation = ConvasationBublblElevation
            ) {
                Image(
                    painter = painterResource(it),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(160.dp),
                    contentDescription = stringResource(id = R.string.attached_image)
                )
            }
        }
    }
}

@Composable
fun ClickableMessage(
    message: Message,
    isUserMe: Boolean,
) {
    val uriHandler = LocalUriHandler.current

    val styledMessage = messageFormatter(
        text = message.content,
        primary = isUserMe
    )

    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = if (isUserMe) MaterialTheme.chattyColors.conversationTextMe
            else MaterialTheme.chattyColors.conversationText
        ),
        modifier = Modifier.padding(16.dp),
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
//                        SymbolAnnotationType.PERSON.name -> if (!isUserMe) authorClicked()
                        else -> Unit
                    }
                }
        }
    )
}


private val ConvasationBublblElevation = 5.dp