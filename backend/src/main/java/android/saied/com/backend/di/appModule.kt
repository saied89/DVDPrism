package android.saied.com.backend.di

import android.saied.com.backend.EnvironmentPropertiesReader
import android.saied.com.backend.fetcher.MovieFetcher
import android.saied.com.backend.fetcher.OmdbFetcher
import android.saied.com.backend.fetcher.OmdbSearcher
import android.saied.com.backend.task.MovieFetcherTask
import org.koin.dsl.module.module

val appModule = module {

    single {
        MovieFetcherTask(
            repository = get(),
            movieFetcher = MovieFetcher.MetacriticFetcher(httpClient = get(HTML_CLIENT)),
            omdbFetcher = OmdbFetcher(
                client = get(JSON_CLIENT),
                envReader = EnvironmentPropertiesReader(get())
            ),
            omdbSearcher = OmdbSearcher(
                client = get(JSON_CLIENT),
                envReader = EnvironmentPropertiesReader(get())
            )
        )
    }

}