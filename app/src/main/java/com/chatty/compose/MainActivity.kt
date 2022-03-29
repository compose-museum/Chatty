package com.chatty.compose

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chatty.compose.screens.chatty.PersonalProfileEditor
import com.chatty.compose.screens.login.Login
import com.chatty.compose.screens.register.Register
import com.chatty.compose.screens.splash.Splash
import com.chatty.compose.ui.components.AppScaffold
import com.chatty.compose.ui.components.AppScreen
import com.chatty.compose.ui.theme.ChattyTheme
import com.chatty.compose.ui.utils.LocalNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ChattyTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Transparent, useDarkIcons)
                }

                val navController = rememberNavController()

                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(navController, AppScreen.main) {
                        composable(AppScreen.splash) {
                            hideIME()
                            Splash()
                        }
                        composable(AppScreen.login) {
                            hideIME()
                            Login()
                        }
                        composable(AppScreen.register) {
                            hideIME()
                            Register()
                        }
                        composable(AppScreen.main) {
                            hideIME()
                            AppScaffold()
                        }
                        composable(
                            "${AppScreen.profileEdit}/{category}",
                            arguments = listOf(navArgument("category") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            hideIME()
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
                    }
                }
            }
        }
    }

    private fun hideIME() {
        with(getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager) {
            hideSoftInputFromWindow((this@MainActivity as Activity).currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}
