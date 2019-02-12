package com.saied.dvdprism.app

import android.app.Application
import com.saied.dvdprism.app.di.appModule
import com.saied.dvdprism.app.di.dbModule
import com.saied.dvdprism.app.di.networkModule
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, networkModule, dbModule))
        Fresco.initialize(this)
        StethoUtil.init(this)
    }
}