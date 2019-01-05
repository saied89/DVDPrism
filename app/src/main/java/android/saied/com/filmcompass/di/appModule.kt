package android.saied.com.filmcompass.di

import android.saied.com.filmcompass.MovieRepository
import android.saied.com.filmcompass.network.MovieFetcher
import android.saied.com.filmcompass.ui.main.MainViewModel
import android.saied.com.filmcompass.ui.main.movieList.MovieListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    viewModel { MainViewModel(get()) }

    viewModel { MovieListViewModel(get()) }

    single {
        MovieRepository(
            movieFetcher = MovieFetcher(get())
        )
    }
}