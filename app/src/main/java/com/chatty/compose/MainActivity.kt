package com.chatty.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        setContent {
            ChattyTheme {

                WindowCompat.setDecorFitsSystemWindows(window, false)
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }


                val navController = rememberNavController()

                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(navController, AppScreen.splash) {
                        composable(AppScreen.splash) { Splash() }
                        composable(AppScreen.login) { Login() }
                        composable(AppScreen.register) { Register() }
                        composable(AppScreen.main) { AppScaffold() }
                    }
                }

            }
        }
    }
}
