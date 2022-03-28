package com.chatty.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatty.compose.database.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository,
) : ViewModel() {

    val loginStatus = repository.loginStatus
    val savedUsers = repository.savedUsers

    fun login(uid: String, pwd: String) {
        viewModelScope.launch {
            repository.login(uid, pwd)
        }
    }

}
