package com.chatty.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chatty.compose.screens.home.mock.friends
import com.chatty.compose.screens.contracts.*
import com.chatty.compose.screens.conversation.ConversationScreen
import com.chatty.compose.screens.conversation.ConversationUiState
import com.chatty.compose.screens.conversation.LocalBackPressedDispatcher
import com.chatty.compose.screens.conversation.mock.initialMessages
import com.chatty.compose.screens.drawer.PersonalProfileEditor
import com.chatty.compose.screens.explorer.CreatePost
import com.chatty.compose.screens.login.Login
import com.chatty.compose.screens.register.Register
import com.chatty.compose.screens.splash.Splash
import com.chatty.compose.ui.components.AppScaffold
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.theme.ChattyTheme
import com.chatty.compose.ui.theme.chattyColors
import com.chatty.compose.ui.utils.LocalNavController
import com.chatty.compose.ui.utils.hideIME
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ChattyTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons =
                    !isSystemInDarkTheme() && MaterialTheme.chattyColors.isLight
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Transparent, useDarkIcons)
                }

                val navController = rememberAnimatedNavController()

                DisposableEffect(Unit) {
                    val destinationChangedListener =
                        NavController.OnDestinationChangedListener { _, _, _ -> hideIME() }
                    navController.addOnDestinationChangedListener(destinationChangedListener)
                    onDispose {
                        navController.removeOnDestinationChangedListener(
                            destinationChangedListener
                        )
                    }
                }

                CompositionLocalProvider(
                    LocalNavController provides navController,
                    LocalBackPressedDispatcher provides onBackPressedDispatcher
                ) {
                    ChattyNavHost(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChattyNavHost(navController: NavHostController) {
    val transSpec = remember { tween<IntOffset>(400) }

    AnimatedNavHost(
        navController = navController,
        startDestination = AppScreen.login,
//        enterTransition = {
//            slideInHorizontally(
//                initialOffsetX = { it },
//                animationSpec = transSpec
//            )
//        },
//        popExitTransition = {
//            slideOutHorizontally(
//                targetOffsetX = { it },
//                animationSpec = transSpec
//            )
//        },
//        exitTransition = {
//            slideOutHorizontally(
//                targetOffsetX = { -it },
//                animationSpec = transSpec
//            )
//        },
//        popEnterTransition = {
//            slideInHorizontally(
//                initialOffsetX = { -it },
//                animationSpec = transSpec
//            )
//        }

    ) {
        composable(
            AppScreen.splash,
            enterTransition = null,
            exitTransition = null
        ) {
            Splash()
        }
        composable(
            AppScreen.login,
            enterTransition = null,
            exitTransition = null
        ) {
            Login()
        }
        composable(
            AppScreen.register,
            enterTransition = null,
            exitTransition = null
        ) {
            Register()
        }
        composable(
            AppScreen.main,
            enterTransition = null,
            exitTransition = null,
        ) {
            AppScaffold()
        }
        composable(
            route = "${AppScreen.userProfile}/{uid}",
            arguments = listOf(navArgument("uid") {
                type = NavType.StringType
            }),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid")!!
            // 待改进
            UserProfile(user = fetchUserInfoById(uid))
        }
        composable(
            route = "${AppScreen.profileEdit}/{category}",
            arguments = listOf(navArgument("category") {
                type = NavType.StringType
            }),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            var category = backStackEntry.arguments?.getString("category")!!
            PersonalProfileEditor(category)
        }
        composable(AppScreen.addFriends) {
            AddFriends(AddFriendsViewModel())
        }
        composable(AppScreen.qr_scan) {
            QRCodeScanner()
        }
        composable(
            route = "${AppScreen.strangerProfile}/{uid}/{from_source}",
            arguments = listOf(
                navArgument("uid") { type = NavType.StringType },
                navArgument("from_source") { type = NavType.StringType },
            ),
            enterTransition = null,
            exitTransition = null
        ) { backStackEntry ->
            var uid = backStackEntry.arguments?.getString("uid")!!
            // 待改进
            var user = friends.find { it.uid == uid }!!

            var fromSource = backStackEntry.arguments?.getString("from_source")!!
            StrangerProfile(user, fromSource)
        }
        composable(
            route = "${AppScreen.conversation}/{uid}",
            arguments = listOf(navArgument("uid") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid")!!
            ConversationScreen(
                uiState = ConversationUiState(
                    initialMessages = initialMessages,
                    conversationUserId = uid
                )
            )
        }
        composable(
            route = AppScreen.createPost
        ) {
            CreatePost()
        }
    }
}

fun fetchUserInfoById(uid: String) = friends.first { it.uid == uid }

typealias AppTheme = MaterialTheme