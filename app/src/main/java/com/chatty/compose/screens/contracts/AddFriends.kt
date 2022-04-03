package com.chatty.compose.screens.contracts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatty.compose.R
import com.chatty.compose.bean.UserProfileData
import com.chatty.compose.screens.chatty.mock.friends
import com.chatty.compose.ui.components.*
import com.chatty.compose.ui.utils.LocalNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.max



// 后续可以提升到 ViewModel 中
var isLoading by mutableStateOf<Boolean>(false)
var displaySearchUsersFlow = MutableStateFlow<List<UserProfileData>>(listOf())

suspend fun refreshFriendSearched(searchContent: String) {
    delay(3000)
    var currentResult = friends.filter {
        it.nickname.lowercase(Locale.getDefault()).contains(searchContent)
    }.toMutableList()
    displaySearchUsersFlow.emit(currentResult)
    isLoading = false
}

@Preview
@Composable
fun AddFriends() {
    var naviController = LocalNavController.current
    var isSearchingState = remember { mutableStateOf(false) }
    var displaySearchUsers = displaySearchUsersFlow.collectAsState()
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
        HeightSpacer(value = 10.dp)
        if(!isSearchingState.value) {
            AddFriendsOtherWay()
        } else {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            LazyColumn {
                displaySearchUsers.value.forEach {
                    item(it.uid) {
                        FriendItem(avatarRes = it.avatarRes, friendName = it.nickname, motto = it.motto) {
                            naviController.navigate("${AppScreen.strangerProfile}/${it.uid}/用户名搜索")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AddFriendTopBar() {
    val navController = LocalNavController.current
    TopBar(
        start = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.back), "add_friends")
            }
        },
        center =  {
            Text("添加联系人")
        },
        backgroundColor = Color.White
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchFriendBar(isSearchingState: MutableState<Boolean>) {
    val scope = rememberCoroutineScope()
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
                        if (!isSearchingState.value) {
                            searchContent = ""
                            displaySearchUsersFlow.tryEmit(emptyList())
                            isLoading = false
                        }
                    },
                textStyle = TextStyle(fontSize = 18.sp),
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        scope.launch {
                            isLoading = true
                            refreshFriendSearched(searchContent)
                        }
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Search
                )
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
                    displaySearchUsersFlow.tryEmit(emptyList())
                    isLoading = false
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

@Composable
fun AddFriendsOtherWay() {
    Column() {
        val navController = LocalNavController.current
        AddFriendsOtherWay(
            R.drawable.qr_code,
            "扫一扫",
            "扫描二维码添加联系人"
        ) {
            navController.navigate(AppScreen.qr_scan)
        }
    }
}

@Composable
fun AddFriendsOtherWay(
    icon: Int,
    functionName: String,
    description: String,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        color = Color(0xFFF8F8F8)
    ) {
        CenterRow(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            Icon(
                painter =  painterResource(id = R.drawable.qr_code),
                contentDescription = "qr_code",
                modifier = Modifier
                    .size(60.dp)
                    .padding(12.dp)
            )
            Spacer(Modifier.padding(horizontal = 10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = functionName,
                    style = MaterialTheme.typography.h6
                )
                Spacer(Modifier.padding(vertical = 3.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            WidthSpacer(4.dp)
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Icon(painter = painterResource(id = R.drawable.expand_right), contentDescription = "")
            }
        }
    }
}
