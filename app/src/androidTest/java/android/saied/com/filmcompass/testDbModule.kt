package android.saied.com.filmcompass

import android.saied.com.filmcompass.db.MovieDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val testDBModule = module {
    single(override = true) {
        Room.inMemoryDatabaseBuilder(
            androidContext().applicationContext,
            MovieDatabase::class.java
        ).allowMainThreadQueries().build().movieDao()
    }
}