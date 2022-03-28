package com.chatty.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatty.compose.database.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AppViewModel(
    repository: UserRepository
): ViewModel() {

    private val currentUser = repository.currentUser

    fun waitAndNavigate(block: (Boolean) -> Unit) {
        viewModelScope.launch {
            block(currentUser.first() != null)
        }
    }

}

data class Screen(
    val route: String,
    val name: String? = null
)

object AppScreen {
    val login = Screen("login")
    val register = Screen("register")
    val home = Screen("home")
    val splash = Screen("splash")
    val search = Screen("search")
}
