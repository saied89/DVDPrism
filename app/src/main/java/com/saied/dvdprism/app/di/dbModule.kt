package com.saied.dvdprism.app.di

import com.saied.dvdprism.app.repository.MovieRepository
import com.saied.dvdprism.app.db.MovieDatabase
import com.saied.dvdprism.app.network.MovieFetcher
import com.saied.dvdprism.app.repository.MovieRepositoryImp
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            MovieDatabase::class.java,
            "Movie_database"
        ).allowMainThreadQueries().build().movieDao()
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