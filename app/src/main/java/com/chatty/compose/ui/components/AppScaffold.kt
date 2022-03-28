package com.chatty.compose.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.chatty.compose.R
import com.chatty.compose.screens.chatty.Chatty
import com.chatty.compose.screens.explorer.Explorer
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppScaffold() {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var selectedScreens by remember { mutableStateOf(0) }

    val screens = listOf(
        Screens("Chatty", R.drawable.chat) { Chatty() },
        Screens("通讯录", R.drawable.list) { },
        Screens("发现", R.drawable.explore) { Explorer() },
        Screens("我", R.drawable.person) {  }
    )

    Scaffold(
        bottomBar = {
            MyBottomNavigationBar(
                selectedScreen = selectedScreens,
                screens = screens,
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
            )
        },
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding(),
    ) {
        HorizontalPager(
            count = screens.size,
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            screens.forEachIndexed { index, screens ->
                when (page) {
                    index -> screens.content()
                }
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedScreens = page
        }
    }
}
