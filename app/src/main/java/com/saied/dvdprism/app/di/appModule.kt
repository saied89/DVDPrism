package com.saied.dvdprism.app.di

import com.saied.dvdprism.app.ui.favoriteList.FavoritesViewModel
import com.saied.dvdprism.app.ui.movieDetail.DetailsViewModel
import com.saied.dvdprism.app.ui.movieDetail.DetailsViewModelImp
import com.saied.dvdprism.app.ui.main.IMainViewModel
import com.saied.dvdprism.app.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    viewModel<IMainViewModel> { MainViewModel(get()) }

    viewModel<DetailsViewModel> { DetailsViewModelImp(get()) }

    viewModel { FavoritesViewModel(movieRepository = get()) }

}