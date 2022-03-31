package com.chatty.compose.screens.contracts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatty.compose.R
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.HeightSpacer
import com.chatty.compose.ui.components.WidthSpacer
import com.chatty.compose.ui.utils.LocalNavController
import com.chatty.compose.ui.utils.hideIME
import kotlinx.coroutines.launch
import kotlin.math.max
import java.util.*


@Preview
@Composable
fun AddFriends() {
    var isSearchingState = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        AnimatedVisibility(visible = !isSearchingState.value) {
            AddFriendTopBar()
        }
        HeightSpacer(value = 5.dp)
        SearchFriendBar(isSearchingState)
    }
}


@Composable
fun AddFriendTopBar() {
    // val navController = LocalNavController.current
    TopAppBar(
        contentPadding = WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues(),
        backgroundColor = Color.White
    ) {
        CenterRow {
            Box (
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("添加联系人", modifier = Modifier.align(Alignment.Center))
                IconButton(
                    onClick = {
                        // navController.popBackStack()
                    },
                    modifier=  Modifier.align(Alignment.BottomStart)
                ) {
                    Icon(painter = painterResource(id = R.drawable.back), "add_friends")
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchFriendBar(isSearchingState: MutableState<Boolean>) {
    val context = LocalContext.current
    var focusManager = LocalFocusManager.current
    var searchContent by remember { mutableStateOf("") }
    var focusRequester = remember { FocusRequester() }
    var rowModifier: Modifier = Modifier
    if (isSearchingState.value) {
        rowModifier = rowModifier.padding(WindowInsets.statusBars.asPaddingValues())
    }
    val interactionSource = remember {
      MutableInteractionSource()
    }
    SubcompositionRow(
        Modifier
            .then(rowModifier)
            .padding(horizontal = 10.dp),
        textField = {
            BasicTextField(
                value = searchContent,
                onValueChange = {
                    searchContent = it
                },
                modifier = Modifier
                    .height(50.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                    .focusable(true, interactionSource)
                    .onFocusChanged {
                        isSearchingState.value = it.isFocused
                    },
                textStyle = TextStyle(fontSize = 18.sp),
                maxLines = 1
            ) { innerText ->
                CenterRow(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp),
                        contentAlignment = if (isSearchingState.value) Alignment.CenterStart else Alignment.Center
                    ) {
                        if (!isSearchingState.value) {
                            CenterRow {
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = null,
                                    tint = Color.LightGray
                                )
                                WidthSpacer(value = 3.dp)
                                Text(
                                    text = "UID/用户名",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.LightGray
                                )
                            }
                        }
                        innerText()
                    }
                    if (searchContent.isNotEmpty()) {
                        IconButton(
                            onClick = { searchContent = "" },
                        ) {
                            Icon(Icons.Filled.Close, null)
                        }
                    }
                }
            }
        },
        cancel = {
            if (isSearchingState.value) {
                TextButton(onClick = {
                    focusManager.clearFocus()
                    isSearchingState.value = false
                    searchContent = ""
                }) {
                    Text(text = "取消")
                }
            }
        }
    )
}


@Composable
fun SubcompositionRow(modifier: Modifier, textField: @Composable () -> Unit, cancel: @Composable () -> Unit) {
    SubcomposeLayout(modifier) { constraints ->
        var cancelMeasureables = subcompose("cancel") { cancel() }
        var cancelPlaceable: Placeable? = null
        if (cancelMeasureables.isNotEmpty()) {
            cancelPlaceable = cancelMeasureables.first().measure(constraints = constraints)
        }
        var consumeWidth = cancelPlaceable?.width ?: 0
        var textFieldMeasureables = subcompose("text_field") { textField() }.first()
        var textFieldPlaceables = textFieldMeasureables.measure(
            Constraints(
                minWidth = constraints.maxWidth - consumeWidth,
                maxWidth = constraints.maxWidth - consumeWidth,
                minHeight = 0,
                maxHeight = constraints.maxHeight
            )
        )
        var width = constraints.maxWidth
        var height = max(cancelPlaceable?.height ?: 0, textFieldPlaceables.height)
        layout(width, height) {
            textFieldPlaceables.placeRelative(0, 0)
            cancelPlaceable?.placeRelative(textFieldPlaceables.width, 0)
        }
    }
}