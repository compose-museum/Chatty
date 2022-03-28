package com.chatty.compose

import android.app.Application
import com.chatty.compose.database.IceDatabase
import com.chatty.compose.database.UserRepository
import com.chatty.compose.viewmodel.LoginViewModel
import com.chatty.compose.viewmodel.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.BuildConfig
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                module {
                    factory(named("IO")) { CoroutineScope(Dispatchers.IO) }
                    single { IceDatabase.getDatabase(get()) }
                    single { UserRepository(get<IceDatabase>().userDao()) }
//          viewModel { AppViewModel(get()) }
                    viewModel { RegisterViewModel(get()) }
//          viewModel { HomeViewModel(get()) }
                    viewModel { LoginViewModel(get()) }
//          viewModel { SearchViewModel(get()) }
                }
            )
        }
    }
}