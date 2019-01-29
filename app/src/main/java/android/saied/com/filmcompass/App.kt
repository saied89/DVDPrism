package android.saied.com.filmcompass

import android.app.Application
import android.saied.com.filmcompass.di.appModule
import android.saied.com.filmcompass.di.dbModule
import android.saied.com.filmcompass.di.networkModule
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, networkModule, dbModule))
        Fresco.initialize(this)
//        GlobalScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    TrueTime
//                        .build()
//                        .withSharedPreferencesCache(this@App)
//                        .initialize()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    Log.e(this@App::class.simpleName, "something went wrong when trying to initialize TrueTime", e)
//                }
//            }
//        }
    }
}