package android.saied.com.backend.fetcher

import android.saied.com.common.model.OmdbDetails
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.URLProtocol

class OmdbFetcher(private val client: HttpClient, private val omdbApiKey: String) {

    private val apiKey_queryLabel = "apikey"
    private val title_queryLabel = "t"

    suspend fun getOmdbDetails(title: String): OmdbDetails = client.get {
        this.url {
            protocol = URLProtocol.HTTP
            host = "www.omdbapi.com"
            parameters.append(apiKey_queryLabel, omdbApiKey)
            parameters.append(title_queryLabel, title)
        }
    }

}