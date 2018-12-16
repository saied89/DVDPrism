package android.saied.com.moviefetcher

import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.request.get

suspend fun fetchHtml(url: String, httpClient: HttpClient = HttpClient()): Try<String> =
    httpClient.use { client ->
        try {
            Try.just(client.get<String>(url))
        } catch (exp: Exception) {
            Try.raise<String>(exp)
        }
    }
