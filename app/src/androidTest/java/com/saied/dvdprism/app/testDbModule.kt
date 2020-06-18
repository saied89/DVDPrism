package com.saied.dvdprism.app

import com.saied.dvdprism.app.db.MovieDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val testDBModule = module {
    single(override = true) {
        Room.inMemoryDatabaseBuilder(
            androidContext().applicationContext,
            MovieDatabase::class.java
        ).allowMainThreadQueries().build().movieDao()
    }
}