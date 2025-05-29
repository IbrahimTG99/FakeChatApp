package com.readychatai.dev

import android.app.Application
import com.readychatai.dev.di.appModule
import com.readychatai.dev.di.databaseModule
import com.readychatai.dev.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)

            modules(appModule,
                databaseModule,
                viewModelModule,)
        }
    }
}
