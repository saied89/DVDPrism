package android.saied.com.backend.di

import android.saied.com.backend.services.MovieFetcherTask
import android.saied.com.moviefetcher.MovieFetcher
import org.koin.dsl.module.module

val appModule = module {

    single {
        MovieFetcherTask(get(), MovieFetcher.MetacriticFetcher(get()))
    }

}