package android.saied.com.filmcompass.di

import android.saied.com.filmcompass.ui.main.MainViewModel
import android.saied.com.filmcompass.ui.movieDetail.DetailsViewModel
import android.saied.com.filmcompass.ui.movieDetail.DetailsViewModelImp
import android.saied.com.filmcompass.ui.movieList.MovieListViewModel
import android.saied.com.filmcompass.ui.movieList.MovieListViewModelImp
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    viewModel { MainViewModel(get()) }

    viewModel<MovieListViewModel> { MovieListViewModelImp(get()) }

    viewModel<DetailsViewModel> { DetailsViewModelImp(get()) }
}