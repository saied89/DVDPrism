package android.saied.com.filmcompass.di

import android.saied.com.filmcompass.repository.MovieRepository
import android.saied.com.filmcompass.db.MovieDatabase
import android.saied.com.filmcompass.network.MovieFetcher
import android.saied.com.filmcompass.repository.MovieRepositoryImp
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            MovieDatabase::class.java,
            "Movie_database"
        ).build().movieDao()
    }

//    single {
//        get<MovieDatabase>().movieDao()
//    }

    single<MovieRepository> {
        MovieRepositoryImp(
            movieFetcher = MovieFetcher(get()),
            movieDAO = get()
        )
    }
}