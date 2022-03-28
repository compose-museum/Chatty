package com.chatty.compose.database

import com.chatty.compose.common.AuthStatus
import com.chatty.compose.database.bean.User
import com.chatty.compose.network.Api
import com.chatty.compose.network.jsonObjects.AuthInfo
import com.github.nthily.ice.database.dao.UserDao
import kotlinx.coroutines.flow.*

class UserRepository(
    private val userDao: UserDao
) {
    private val _loginStatus = MutableStateFlow(AuthStatus.UNAUTH)
    val loginStatus: StateFlow<AuthStatus> = _loginStatus.asStateFlow()

    val currentUser
        get() = userDao.userWithToken()

    val savedUsers
        get() = userDao.loadUserByTimeStamp()

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    fun userWithToken(): Flow<User?> {
        return userDao.userWithToken()
    }

    suspend fun loadUserByName(username: String): User? {
        return userDao.loadUserByName(username)
    }

    fun loadUserByTimeStamp(): Flow<List<User>> {
        return userDao.loadUserByTimeStamp()
    }

    suspend fun login(uid:String, pwd: String) {
        _loginStatus.emit(AuthStatus.AUTHING)
        try {
            val result = Api.auth(AuthInfo(uid, pwd))
            val timestamp = System.currentTimeMillis()

            // 初始化用户信息缓存
            // 判断是否有本地用户缓存，如果有，则更新 token，否则创建一个新的用户缓存

//            缓存逻辑暂时先去掉
//            val exist = loadUserByName(uid)
//            if (exist != null) {
//                updateUser(exist.copy(accessToken = result.accessToken, timestamp = timestamp))
//            } else {
//                val user = User(uid, null, result.accessToken, timestamp)
//                insertUser(user)
//            }


            _loginStatus.emit(AuthStatus.AUTHED)
        } catch (e: Exception) {
            e.printStackTrace()
            _loginStatus.emit(AuthStatus.FALSE)
        }
    }

    suspend fun logout() {
        _loginStatus.emit(AuthStatus.UNAUTH)
        userDao.logoutCurrentUser()
    }

}
