package com.chatty.compose.screens.explorer

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
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
                    if (targetState > initialState) {
                        scaleIn(tween(600)) with scaleOut()
                    } else {
                        scaleIn(tween(600)) with scaleOut()
                    }
                }
            ) {
                when (targetState) {
                    0f -> {
                        FloatingActionButton(
                            onClick = {
                                editAction()
                            },
                            backgroundColor = Color.White,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(Icons.Rounded.Edit, null, tint = Color.Black)
                        }
                    }
                    1f -> {
                        FloatingActionButton(
                            onClick = {
                                arrowAction()
                            },
                            backgroundColor = Color.White,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(painterResource(id = R.drawable.arrow_upward), null, tint = Color.Black)
                        }
                    }
                }
            }
        }
    }
}
