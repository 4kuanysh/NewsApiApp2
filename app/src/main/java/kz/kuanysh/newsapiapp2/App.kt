package kz.kuanysh.newsapiapp2

import android.app.Application
import kz.kuanysh.newsapiapp2.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(dbModule, remoteModule, repoModule, domainModule, viewModelModule))
        }
    }

}