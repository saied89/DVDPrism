package com.saied.dvdprism.backend.di

import com.saied.dvdprism.backend.EnvironmentPropertiesReader
import com.saied.dvdprism.backend.fetcher.MovieFetcher
import com.saied.dvdprism.backend.fetcher.OmdbFetcher
import com.saied.dvdprism.backend.fetcher.OmdbSearcher
import com.saied.dvdprism.backend.task.MovieFetcherTask
import org.koin.dsl.module.module

val appModule = module {

    single {
        MovieFetcherTask(
            repository = get(),
            movieFetcher = MovieFetcher(httpClient = get(HTML_CLIENT)),
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