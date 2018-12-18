package android.saied.com.filmcompass

import android.saied.com.moviefetcher.MovieFetcher

class MovieRepository() {

    suspend fun fetchMovies() =
        MovieFetcher.MetacriticFetcher().fetchMovies()

}