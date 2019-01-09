package android.saied.com.filmcompass.di

import android.saied.com.filmcompass.MovieRepository
import android.saied.com.filmcompass.db.MovieDatabase
import android.saied.com.filmcompass.network.MovieFetcher
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            MovieDatabase::class.java,
            "Movie_database"
        )
    }

    single {
        get<MovieDatabase>().movieDao()
    }

    single {
        MovieRepository(
            movieFetcher = MovieFetcher(get())
        )
    }
}