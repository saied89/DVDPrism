package android.saied.com.filmcompass.network

import android.saied.com.common.model.Movie
import arrow.core.Try
import io.ktor.client.HttpClient
import io.ktor.client.call.ReceivePipelineException
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import java.io.IOException
import java.net.ConnectException

class MovieFetcher(private val httpClient: HttpClient) {
    suspend fun fetchMovies(): Try<List<Movie>> =
        try {
            val res: List<Movie> = httpClient.get {
                url {
                    protocol = URLProtocol.HTTP
                    host = "192.168.1.2"
                    port = 8080
                }
            }
            Try.just(res)
        } catch (exp: ReceivePipelineException) {
            Try.raise(exp)
        } catch (exp: ConnectException) {
            Try.raise(exp)
        } catch (exp: IOException) {
            Try.raise(exp)
        }
}