package android.saied.com.filmcompass

import android.saied.com.filmcompass.Network.MovieFetcher
import android.saied.com.filmcompass.parsing.FetchTask
import arrow.core.Try
import arrow.core.success
import io.ktor.client.HttpClient

class MovieRepository(private val httpClient: HttpClient = HttpClient()) {

    suspend fun fetchMovies() =
        MovieFetcher.MetacriticFetcher().fetchMovies()

}