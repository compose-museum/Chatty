package com.chatty.compose.screens.contracts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatty.compose.R
import com.chatty.compose.screens.chatty.mock.friends
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.components.CenterRow
import com.chatty.compose.ui.components.CircleShapeImage
import com.chatty.compose.ui.theme.ok
import com.chatty.compose.ui.utils.LocalNavController
import com.github.promeg.pinyinhelper.Pinyin
import kotlinx.coroutines.launch


data class AlphaState(
    val alpha: Char,
    var state: MutableState<Boolean>
)

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun Contracts() {
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
    var currentSelectedAlphaIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        ContractTopBar()
        var sortedFriends = friends.groupBy {
            Pinyin.toPinyin(it.nickname.first()).first()
        }.toSortedMap()
        val preSumIndexToStateMap = remember(sortedFriends) { mutableMapOf<Int, AlphaState>() }
        val alphaCountPreSumList = remember(sortedFriends) {
            var currentSum = 0
            var index = 0
            var result = mutableListOf<Int>()
            for ((alpha, friendList) in sortedFriends) {
                preSumIndexToStateMap[index] = AlphaState(alpha, mutableStateOf(false))
                result.add(currentSum)
                currentSum += (friendList.size + 1)
                index++
            }
            result
        }
        var lazyListState = rememberLazyListState()
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = lazyListState
            ) {
                sortedFriends.forEach { it ->
                    item {
                        Text(
                            text = it.key.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF2F4FB))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            fontWeight = FontWeight.W700,
                            color = Color(0xFF0079D3)
                        )
                    }
                    itemsIndexed(it.value) { index, user ->
                        FriendItem(user.avatarRes, user.nickname, user.motto) {
                            navController.navigate("${AppScreen.userProfile}/${user.uid}")
                        }
                    }
                }
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                AlphaGuildBar(preSumIndexToStateMap.values) {
                    currentSelectedAlphaIndex = it
                    scope.launch {
                        lazyListState.scrollToItem(alphaCountPreSumList[currentSelectedAlphaIndex])
                        currentSelectedAlphaIndex = binarySearch(alphaCountPreSumList, lazyListState.firstVisibleItemIndex)
                    }
                }
            }
        }
        LaunchedEffect(lazyListState.firstVisibleItemIndex) {
            currentSelectedAlphaIndex = binarySearch(alphaCountPreSumList, lazyListState.firstVisibleItemIndex)
        }

        LaunchedEffect(currentSelectedAlphaIndex) {
            var alphaState = preSumIndexToStateMap[currentSelectedAlphaIndex]!!
            preSumIndexToStateMap.values.forEach {
                it.state.value = false
            }
            alphaState.state.value = true
        }
    }
}

private fun binarySearch(preSum: List<Int>, target: Int): Int {
    var l = -1
    var r = preSum.size - 1
    while (l < r) {
        var mid = l - (l - r - 1) / 2
        if (preSum[mid] <= target) {
            l = mid
        } else {
            r = mid - 1
        }
    }
    return l
}

@Composable
fun ContractTopBar() {
    TopAppBar(
        contentPadding = WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues(),
        backgroundColor = Color.White
    ) {
        CenterRow {
            Box (
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("通讯录", modifier = Modifier.align(Alignment.Center))
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier=  Modifier.align(Alignment.BottomEnd)
                ) {
                    Icon(painter = painterResource(id = R.drawable.add_friend), "add_friends")
                }
            }
        }
    }
}


private val friendItemHeight = 90.dp

@Composable
fun FriendItem(
    avatarRes: Int,
    friendName: String,
    motto: String,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(friendItemHeight)
            .clickable {
                onClick()
            },
        color = Color(0xFFF8F8F8)
    ) {
        CenterRow(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            CircleShapeImage(60.dp, painter = painterResource(id = avatarRes))
            Spacer(Modifier.padding(horizontal = 10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = friendName,
                    style = MaterialTheme.typography.h6
                )
                Spacer(Modifier.padding(vertical = 3.dp))
                Text(
                    text = motto,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AlphaGuildBar(alphaStates: MutableCollection<AlphaState>, onClick: (Int) -> Unit) {
    Column() {
        alphaStates.forEachIndexed { index: Int, alphaState: AlphaState ->
            var boxStyleModifier = Modifier
                .size(28.dp)
                .padding(2.dp)
                .clip(CircleShape)
            if (alphaState.state.value) {
                boxStyleModifier = boxStyleModifier.background(ok)
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = boxStyleModifier.clickable {
                    onClick(index)
                }
            ) {
                Text(text = alphaState.alpha.toString(), color = if (alphaState.state.value) Color.White else Color.Black)
            }
        }
    }
}


//@Preview
//@Composable
//fun GuildBarDemo() {
//    AlphaGuildBar(mutableSetOf('a', 'c', 'd', 'e', 'f', 'h', 'n', 'v', 's'))
//}