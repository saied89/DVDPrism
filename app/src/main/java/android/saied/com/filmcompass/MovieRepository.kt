package android.saied.com.filmcompass

import android.saied.com.filmcompass.network.MovieFetcher


class MovieRepository(
    private val movieFetcher: MovieFetcher
) {

    suspend fun fetchMovies() =
        movieFetcher.fetchMovies()

}