package com.saied.dvdprism.backend.fetcher

import com.saied.dvdprism.backend.EnvironmentPropertiesReader
import com.saied.dvdprism.backend.model.OmdbSearch
import com.saied.dvdprism.common.model.OmdbType
import arrow.core.Success
import arrow.core.Try
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.client.HttpClient
import io.ktor.client.call.ReceivePipelineException
import io.ktor.client.features.BadResponseStatusException
import io.ktor.client.request.get
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder

const val OMDB_HOST = "www.omdbapi.com"
private const val search_queryLabel = "s"

class OmdbSearcher(private val client: HttpClient, private val envReader: EnvironmentPropertiesReader) {

    private suspend fun searchOmdbByMovie(title: String): Try<OmdbSearch> {
        val url = URLBuilder(
            host = OMDB_HOST,
            parameters = ParametersBuilder(2).apply {
                append(apiKey_queryLabel, envReader.getOmdbApiKey())
                append(search_queryLabel, title)
            }
        ).build()
        return try {
            val res: OmdbSearch = client.get(url)
            Try.just(res)
        } catch (exp: ReceivePipelineException) {
            if (exp.cause is MissingKotlinParameterException)
                Try.raise(OMDBMovieNotFoundException(url.toString()))
            else
                Try.raise(exp.cause)
        } catch (exp: BadResponseStatusException) {
            Try.raise(exp)
        }
    }

    suspend fun getImdbId(title: String, dvdYear: Int): Try<String> = searchOmdbByMovie(title).let { searchTry ->
        if (searchTry is Try.Failure)
            Try.raise(searchTry.exception)
        else {
            val omdbSearch = (searchTry as Success).value
            omdbSearch.search
                .filter { it.type == OmdbType.MOVIE }
                .filter {
                    title.toLowerCase() == it.title.toLowerCase() && (dvdYear - it.year <= 2)
                }.maxBy {
                    it.year
                }.let {
                    if (it == null)
                        Try.raise(IllegalStateException("No appropriate search result"))
                    else
                        Try.just(it.imdbID)
                }
        }
    }
}