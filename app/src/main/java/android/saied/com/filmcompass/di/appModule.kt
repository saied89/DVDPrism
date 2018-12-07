package android.saied.com.filmcompass.di

import android.saied.com.filmcompass.MovieRepository
import android.saied.com.filmcompass.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    viewModel {
        MainViewModel(get())
    }

    single {
        MovieRepository()
    }
}