package android.saied.com.filmcompass

import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchHtml(url: String, httpClient: HttpClient = HttpClient()): Try<String> =
    withContext(Dispatchers.IO) {
        httpClient.use { client ->
            try {
                Try.just(client.get<String>(url))
            } catch (exp: Exception) {
                Try.raise<String>(exp)
            }
        }
    }