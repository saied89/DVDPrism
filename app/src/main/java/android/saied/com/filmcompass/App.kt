package android.saied.com.filmcompass

import android.app.Application
import android.saied.com.filmcompass.di.appModule
import android.saied.com.filmcompass.di.dbModule
import android.saied.com.filmcompass.di.networkModule
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, networkModule, dbModule))
        Fresco.initialize(this)
        Stetho.initializeWithDefaults(this)
    }
}