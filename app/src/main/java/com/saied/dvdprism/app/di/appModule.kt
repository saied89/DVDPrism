package com.saied.dvdprism.app.di

import com.saied.dvdprism.app.ui.favoriteList.FavoritesViewModel
import com.saied.dvdprism.app.ui.movieDetail.DetailsViewModel
import com.saied.dvdprism.app.ui.movieDetail.DetailsViewModelImp
import com.saied.dvdprism.app.ui.main.IMainViewModel
import com.saied.dvdprism.app.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<IMainViewModel>(override = true) { MainViewModel(get()) }

    viewModel<DetailsViewModel>(override = true) { DetailsViewModelImp(get()) }

    viewModel(override = true) { FavoritesViewModel(movieRepository = get()) }

}