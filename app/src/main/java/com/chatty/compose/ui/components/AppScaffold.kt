package com.chatty.compose.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import com.chatty.compose.R
import com.chatty.compose.screens.chatty.Chatty
import com.chatty.compose.screens.drawer.PersonalProfile
import com.chatty.compose.screens.contracts.Contracts
import com.chatty.compose.screens.explorer.Explorer
import com.chatty.compose.ui.utils.LocalScaffoldState
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
    val scaffoldState = rememberScaffoldState()

    val screens = listOf(
        Screens("Chatty", R.drawable.chat) { Chatty() },
        Screens("通讯录", R.drawable.list) { Contracts() },
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
        drawerContent = {
           PersonalProfile()
        },
        scaffoldState = scaffoldState
    ) {
        CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
            HorizontalPager(
                count = screens.size,
                state = pagerState,
                userScrollEnabled = false,
                contentPadding = it
            ) { page ->
                screens.forEachIndexed { index, screens ->
                    when (page) {
                        index -> screens.content()
                    }
                }
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedScreens = page
        }
    }
    
    BackHandler(scaffoldState.drawerState.isOpen) {
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }
    
}
