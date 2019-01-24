package android.saied.com.backend.di

import android.saied.com.backend.EnviromentPropertiesReader
import android.saied.com.backend.fetcher.OmdbFetcher
import android.saied.com.backend.fetcher.OmdbSearcher
import android.saied.com.backend.task.MovieFetcherTask
import android.saied.com.moviefetcher.MovieFetcher
import io.ktor.http.httpDateFormat
import org.koin.dsl.module.module

val appModule = module {

    single {
        MovieFetcherTask(
            repository = get(),
            movieFetcher = MovieFetcher.MetacriticFetcher(httpClient = get(HTML_CLIENT)),
            omdbFetcher = OmdbFetcher(
                client = get(JSON_CLIENT),
                envReader = EnviromentPropertiesReader(get())
            ),
            omdbSearcher = OmdbSearcher(
                client = get(JSON_CLIENT),
                envReader = EnviromentPropertiesReader(get())
            )
        )
    }

}