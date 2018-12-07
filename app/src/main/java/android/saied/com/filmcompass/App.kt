package android.saied.com.filmcompass

import android.app.Application
import android.saied.com.filmcompass.di.appModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}