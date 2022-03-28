package com.chatty.compose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chatty.compose.database.bean.User
import com.github.nthily.ice.database.dao.UserDao

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class IceDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    companion object {

        @Volatile
        private var INSTANCE: IceDatabase? = null

        fun getDatabase(context: Context): IceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IceDatabase::class.java,
                    "IceDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
