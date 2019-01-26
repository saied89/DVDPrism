package android.saied.com.backend.fetcher

import android.saied.com.backend.EnvironmentPropertiesReader
import android.saied.com.common.model.OmdbDetails
import arrow.core.Try
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.client.HttpClient
import io.ktor.client.call.ReceivePipelineException
import io.ktor.client.features.BadResponseStatusException
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url

const val apiKey_queryLabel = "apikey"
private const val title_queryLabel = "t"
private const val year_querryLabel = "y"
private const val id_querryLabel = "i"

class OmdbFetcher(private val client: HttpClient, private val envReader: EnvironmentPropertiesReader) {

    suspend fun getOmdbDetailsById(imdbId: String): Try<OmdbDetails> {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTP
            host = OMDB_HOST
            parameters.append(apiKey_queryLabel, envReader.getOmdbApiKey())
            parameters.append(id_querryLabel, imdbId)
        }
        return getOmdbDetailsByUrl(url.build())
    }

    suspend fun getOmdbDetailsByTitle(title: String, year: Int? = null): Try<OmdbDetails> {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTP
            host = OMDB_HOST
            parameters.append(apiKey_queryLabel, envReader.getOmdbApiKey())
            parameters.append(title_queryLabel, title)
            if (year != null)
                parameters.append(year_querryLabel, year.toString())
        }
        return getOmdbDetailsByUrl(url.build())
    }

    private suspend fun getOmdbDetailsByUrl(url: Url): Try<OmdbDetails> =
        try {
            val res: OmdbDetails = client.get(url)
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

class OMDBMovieNotFoundException(title: String) : Exception("$title was not found")
