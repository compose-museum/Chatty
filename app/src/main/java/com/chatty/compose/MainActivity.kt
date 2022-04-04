package com.chatty.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chatty.compose.screens.chatty.mock.friends
import com.chatty.compose.screens.contracts.AddFriends
import com.chatty.compose.screens.contracts.QrCodeScan
import com.chatty.compose.screens.contracts.StrangerProfile
import com.chatty.compose.screens.contracts.UserProfile
import com.chatty.compose.screens.drawer.PersonalProfileEditor
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
                val useDarkIcons = MaterialTheme.colors.isLight && MaterialTheme.chattyColors.isLight

                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Transparent, useDarkIcons)
                }

                val navController = rememberAnimatedNavController()

                CompositionLocalProvider(LocalNavController provides navController) {
                    AnimatedNavHost(navController, AppScreen.main) {
                        composable(
                            AppScreen.splash,
                            enterTransition = null,
                            exitTransition = null
                        ) {
                            hideIME()
                            Splash()
                        }
                        composable(
                            AppScreen.login,
                            enterTransition = null,
                            exitTransition = null
                        ) {
                            hideIME()
                            Login()
                        }
                        composable(
                            AppScreen.register,
                            enterTransition = null,
                            exitTransition = null
                        ) {
                            hideIME()
                            Register()
                        }
                        composable(
                            AppScreen.main,
                            enterTransition = null,
                            exitTransition = null,
                        ) {
                            hideIME()
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
                            var uid = backStackEntry.arguments?.getString("uid")!!
                            // 待改进
                            var user = friends.find { it.uid == uid }!!
                            UserProfile(user = user)
                        }
                        composable(
                            route = "${AppScreen.profileEdit}/{category}",
                            arguments = listOf(navArgument("category") {
                                type = NavType.StringType
                            }),
                            enterTransition = null,
                            exitTransition = null
                        ) { backStackEntry ->
                            var category = backStackEntry.arguments?.getString("category")
                            var title = when (category) {
                                "gender" -> "选择性别"
                                "age" -> "输入年龄"
                                "phone" -> "输入电话号"
                                "email" -> "输入电子邮箱"
                                else -> "展示二维码"
                            }
                            PersonalProfileEditor(title, category == "gender", category == "qrcode")
                        }
                        composable(AppScreen.addFriends) {
                            hideIME()
                            AddFriends()
                        }
                        composable(AppScreen.qr_scan) {
                            hideIME()
                            QrCodeScan()
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
                            hideIME()
                            var uid = backStackEntry.arguments?.getString("uid")!!
                            // 待改进
                            var user = friends.find { it.uid == uid }!!

                            var fromSource = backStackEntry.arguments?.getString("from_source")!!
                            StrangerProfile(user, fromSource)
                        }
                    }
                }
            }
        }
    }
}
