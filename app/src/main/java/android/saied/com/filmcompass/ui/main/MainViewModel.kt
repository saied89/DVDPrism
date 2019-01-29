package android.saied.com.filmcompass.ui.main

import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class MainViewModel(protected val movieRepo: MovieRepository) : ViewModel() {
    abstract val stateLiveData: LiveData<MainState>
    abstract fun refreshMovies()
}