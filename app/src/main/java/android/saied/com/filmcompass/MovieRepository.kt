package android.saied.com.filmcompass

import android.saied.com.moviefetcher.MovieFetcher
import io.ktor.client.HttpClient

class MovieRepository(private val httpClient: HttpClient = HttpClient()) {

    suspend fun fetchMovies() =
        MovieFetcher.MetacriticFetcher().fetchMovies()

}