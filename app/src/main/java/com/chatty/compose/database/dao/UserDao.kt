package com.github.nthily.ice.database.dao

import androidx.room.*
import com.chatty.compose.database.bean.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun loadUserByName(username: String): User?

    @Query("SELECT * FROM user ORDER BY timestamp DESC")
    fun loadUserByTimeStamp(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE accessToken is not null")
    fun userWithToken(): Flow<User?>

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("UPDATE user set accessToken = null where accessToken is not null")
    suspend fun logoutCurrentUser()

    @Delete
    suspend fun delete(user: User)
}
