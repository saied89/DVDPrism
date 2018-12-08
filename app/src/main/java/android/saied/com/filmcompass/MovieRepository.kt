package android.saied.com.filmcompass

import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val TEST_URL = "https://www.alef.ir/"

class MovieRepository(private val httpClient: HttpClient = HttpClient()) {
    suspend fun getRottenTomatoes(): Try<String> =
        withContext(Dispatchers.IO) {
            httpClient.use { client ->
                try {
                    Try.just(client.get<String>(TEST_URL))
                } catch (exp: Exception){
                    Try.raise<String>(exp)
                }
            }
        }
}