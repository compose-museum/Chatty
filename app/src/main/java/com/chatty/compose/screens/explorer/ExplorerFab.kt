package com.chatty.compose.screens.explorer

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chatty.compose.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExplorerFab(
    boxScope: BoxScope,
    targetState: Float,
    editAction: () -> Unit,
    arrowAction: () -> Unit,
) {
    boxScope.apply {
        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            color = Color.Transparent
        ) {
            AnimatedContent(
                targetState = targetState,
                transitionSpec = {
                    scaleIn(tween(400)) with scaleOut()
                }
            ) {
                when (targetState) {
                    0f -> {
                        ExtendedFloatingActionButton(
                            onClick = {
                                editAction()
                            },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(Icons.Rounded.Edit, null)
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(text = "发表新鲜事")
                        }
                    }
                    1f -> {
                        SmallFloatingActionButton(
                            onClick = {
                                arrowAction()
                            },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(painterResource(id = R.drawable.arrow_upward), null)
                        }
                    }
                }
            }
        }
    }
}
