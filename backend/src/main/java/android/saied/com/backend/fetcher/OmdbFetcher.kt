package android.saied.com.backend.fetcher

import android.saied.com.backend.EnviromentPropertiesReader
import android.saied.com.common.model.OmdbDetails
import arrow.core.Try
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.client.HttpClient
import io.ktor.client.call.ReceivePipelineException
import io.ktor.client.features.BadResponseStatusException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol

class OmdbFetcher(private val client: HttpClient, private val envReader: EnviromentPropertiesReader) {

    private val apiKey_queryLabel = "apikey"
    private val title_queryLabel = "t"
    private val year_querryLabel = "y"
    private val id_querryLabel = "i"

    suspend fun getOmdbDetails(title: String): Try<OmdbDetails> =
        try {
            val res: OmdbDetails = client.get {
                this.url {
                    protocol = URLProtocol.HTTP
                    host = "www.omdbapi.com"
                    parameters.append(apiKey_queryLabel, envReader.getOmdbApiKey())
                    if(disambiguationMap.containsKey(title))
                        parameters.append(id_querryLabel, disambiguationMap[title]!!)
                    else {
                        parameters.append(title_queryLabel, title)
                    }
                }
            }
            Try.just(res)
        } catch (exp: ReceivePipelineException) {
            if(exp.cause is MissingKotlinParameterException)
                Try.raise(OMDBMovieNotFoundException(title))
            else
                Try.raise(exp.cause)
        } catch (exp: BadResponseStatusException) {
            Try.raise(exp)
        }
}

class OMDBMovieNotFoundException(title: String): Exception("$title was not found")

val disambiguationMap = mapOf(
    "Trouble" to "tt5689632",
    "A.X.L." to "tt5709188"
)