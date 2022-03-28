package com.chatty.compose.ui.pages

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.chatty.compose.viewmodel.AppScreen
import com.chatty.compose.viewmodel.LoginViewModel
import com.chatty.compose.viewmodel.RegisterViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun App() {
//  val appViewModel = getViewModel<AppViewModel>()
//  val homeViewModel = getViewModel<HomeViewModel>()
  val registerViewModel = getViewModel<RegisterViewModel>()
  val loginViewModel = getViewModel<LoginViewModel>()
//  val searchViewModel = getViewModel<SearchViewModel>()

  val navController = rememberAnimatedNavController()
  val systemUiController = rememberSystemUiController()
  val useDarkIcons = MaterialTheme.colors.isLight

  SideEffect {
    systemUiController.setStatusBarColor(Color.Transparent, useDarkIcons)
  }

  AnimatedNavHost(
    navController = navController,
    startDestination = AppScreen.login.route,
  ) {
    composable(
      route = AppScreen.login.route,
      enterTransition = {
        when(initialState.destination.route) {
          else -> null
        }
      },
      exitTransition = {
        when(targetState.destination.route) {
          AppScreen.register.route -> slideOutOfContainer(
            towards = AnimatedContentScope.SlideDirection.Left,
            animationSpec = tween(500)
          )
          AppScreen.home.route -> slideOutOfContainer(
            towards = AnimatedContentScope.SlideDirection.Left,
            animationSpec = tween(500)
          )
          else -> null
        }
      }
    ) {
      hideIME()
      Login(navController, loginViewModel)
    }
    composable(
      route = AppScreen.register.route,
      enterTransition = {
        when(initialState.destination.route) {
          AppScreen.login.route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(500))
          else -> null
        }
      }
    ) {
      hideIME()
      Register(navController, registerViewModel)
    }

    composable(
      route = AppScreen.home.route,
      enterTransition = {
        when(initialState.destination.route) {
          AppScreen.search.route -> slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(500))
          else -> null
        }
      },
      exitTransition = {
        when(targetState.destination.route) {
          AppScreen.search.route ->
            slideOutOfContainer(
              AnimatedContentScope.SlideDirection.Left,
              animationSpec = tween(500)
            )
          else -> null
        }
      }
    ) {
      hideIME()
      Home()
    }

//    composable(
//      route = AppScreen.splash.route
//    ) {
//      Splash(navController, appViewModel)
//    }
//
//    composable(
//      route = AppScreen.search.route,
//      enterTransition = {
//        when(initialState.destination.route) {
//          AppScreen.home.route ->
//            slideIntoContainer(
//              AnimatedContentScope.SlideDirection.Left,
//              animationSpec = tween(500)
//            )
//          else -> null
//        }
//      },
//      exitTransition = {
//        when(targetState.destination.route) {
//          AppScreen.home.route ->
//            slideOutOfContainer(
//              AnimatedContentScope.SlideDirection.Right,
//              animationSpec = tween(500)
//            )
//          else -> null
//        }
//      }
//    ) {
//      Search(navController, searchViewModel)
//    }

  }
}

@Composable
fun hideIME() {
  with(LocalContext.current) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
      hideSoftInputFromWindow((this@with as Activity).currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
  }
}
