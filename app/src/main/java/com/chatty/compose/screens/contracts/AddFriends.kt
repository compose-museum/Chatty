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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.max


class AddFriendsViewModel() {
    var isSearching by mutableStateOf<Boolean>(false)
    var isLoading by mutableStateOf<Boolean>(false)
    var searchContent by mutableStateOf<String>("")
    var displaySearchUsersFlow = MutableStateFlow<List<UserProfileData>>(listOf())
    suspend fun refreshFriendSearched() {
        delay(3000)
        var currentResult = friends.filter {
            it.nickname.lowercase(Locale.getDefault()).contains(searchContent)
        }.toMutableList()
        displaySearchUsersFlow.emit(currentResult)
        isLoading = false
    }

    fun clearSearchStatus() {
        displaySearchUsersFlow.tryEmit(emptyList())
        isLoading = false
        isSearching = false
    }
}



@Composable
fun AddFriends(viewModel: AddFriendsViewModel) {
    var naviController = LocalNavController.current
    var displaySearchUsers = viewModel.displaySearchUsersFlow.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.chattyColors.backgroundColor)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        AnimatedVisibility(visible = !viewModel.isSearching) {
            AddFriendTopBar()
        }
        HeightSpacer(value = 5.dp)
        SearchFriendBar(viewModel)
        HeightSpacer(value = 10.dp)
        if(!viewModel.isSearching) {
            AddFriendsOtherWay()
        } else {
            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = MaterialTheme.chattyColors.textColor)
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
                Icon(painter = painterResource(id = R.drawable.back), "add_friends", tint = MaterialTheme.chattyColors.iconColor)
            }
        },
        center =  {
            Text("添加联系人", color = MaterialTheme.chattyColors.textColor)
        },
        backgroundColor = MaterialTheme.chattyColors.backgroundColor,
        contentPadding = AppBarDefaults.ContentPadding
    )
}

@Composable
fun SearchFriendBar(viewModel: AddFriendsViewModel) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    SubcomposeSearchFriendRow(
        modifier = Modifier.padding(horizontal = 10.dp),
        textField = {
            BasicTextField(
                value = viewModel.searchContent,
                onValueChange = {
                    viewModel.searchContent = it
                },
                modifier = Modifier
                    .height(50.dp)
                    .border(1.dp, MaterialTheme.chattyColors.textColor, RoundedCornerShape(5.dp))
                    .onFocusChanged {
                        viewModel.isSearching = it.isFocused
                        if (!viewModel.isSearching) {
                            viewModel.clearSearchStatus()
                        }
                    },
                textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.chattyColors.textColor),
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        scope.launch {
                            viewModel.isLoading = true
                            viewModel.refreshFriendSearched()
                        }
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Search
                ),
                cursorBrush = SolidColor(MaterialTheme.chattyColors.textColor)
            ) { innerText ->
                CenterRow(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp),
                        contentAlignment = if (viewModel.isSearching) Alignment.CenterStart else Alignment.Center
                    ) {
                        if (!viewModel.isSearching) {
                            CenterRow {
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = null,
                                    tint = MaterialTheme.chattyColors.iconColor
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
                    if (viewModel.searchContent.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.searchContent = "" },
                        ) {
                            Icon(Icons.Filled.Close, null, tint = MaterialTheme.chattyColors.iconColor)
                        }
                    }
                }
            }
        },
        cancel = {
            if (viewModel.isSearching) {
                TextButton(onClick = {
                    viewModel.clearSearchStatus()
                    focusManager.clearFocus()
                }) {
                    Text(text = "取消", color = MaterialTheme.chattyColors.textColor)
                }
            }
        }
    )
}


@Composable
fun SubcomposeSearchFriendRow(modifier: Modifier, textField: @Composable () -> Unit, cancel: @Composable () -> Unit) {
    SubcomposeLayout(modifier) { constraints ->
        var cancelMeasureables = subcompose("cancel") { cancel() }
        var cancelPlaceable: Placeable? = null
        if (cancelMeasureables.isNotEmpty()) {
            cancelPlaceable = cancelMeasureables.first().measure(constraints = constraints)
        }
        var consumeWidth = cancelPlaceable?.width ?: 0
        var textFieldMeasureables = subcompose("text_field") { textField() }.first()
        var textFieldPlaceables = textFieldMeasureables.measure(
            constraints.copy(
                minWidth = constraints.maxWidth - consumeWidth,
                maxWidth = constraints.maxWidth - consumeWidth
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
        AddFriendsOtherWayItem(
            R.drawable.qr_code,
            "扫一扫",
            "扫描二维码添加联系人"
        ) {
            navController.navigate(AppScreen.qr_scan)
        }
    }
}

@Composable
fun AddFriendsOtherWayItem(
    icon: Int,
    functionName: String,
    description: String,
    onClick: () -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.chattyColors.backgroundColor)
        .clickable {
            onClick()
        }
    ){
        CenterRow(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            Icon(
                painter =  painterResource(id = R.drawable.qr_code),
                contentDescription = "qr_code",
                tint = MaterialTheme.chattyColors.iconColor,
                modifier = Modifier
                    .size(60.dp)
                    .padding(12.dp)
            )
            WidthSpacer(value = 10.dp)
            Column{
                Text(
                    text = functionName,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.chattyColors.textColor
                )
                HeightSpacer(3.dp)
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.chattyColors.textColor
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.expand_right),
            contentDescription = "",
            tint = MaterialTheme.chattyColors.iconColor,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
