package com.chatty.compose.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.chatty.compose.screens.home.Home
import com.chatty.compose.screens.contracts.Contracts
import com.chatty.compose.screens.drawer.PersonalProfile
import com.chatty.compose.screens.explorer.Explorer
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn( ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AppScaffold() {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var selectedScreen by remember { mutableStateOf(0) }

    ModalNavigationDrawer(
        drawerContent = {
            PersonalProfile()
        },
        drawerState = drawerState,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Scaffold(
            bottomBar = {
                MyBottomNavigationBar(
                    selectedScreen = selectedScreen,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(it)
                        }
                    }
                )
            }
        ) {
            HorizontalPager(
                count = BottomScreen.values().size,
                state = pagerState,
                userScrollEnabled = false,
                contentPadding = it
            ) { page ->
                BottomScreen.values().forEachIndexed{ index, bottomScreen ->
                    when (page) {
                        index -> {
                            when(bottomScreen) {
                                BottomScreen.Message -> Home(drawerState)
                                BottomScreen.Contract -> Contracts()
                                BottomScreen.Explore -> Explorer()
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedScreen = page
        }
    }
    
    BackHandler(drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }
    
}
