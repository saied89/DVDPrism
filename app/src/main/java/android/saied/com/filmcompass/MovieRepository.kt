package android.saied.com.filmcompass

import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    suspend fun getRottenTomatoes(): Try<String> =
        withContext(Dispatchers.IO) {
            HttpClient().use { client ->
                try {
                    Try.just(client.get<String>("https://www.alef.ir/"))
                } catch (exp: Exception){
                    Try.raise<String>(exp)
                }
            }
        }
}