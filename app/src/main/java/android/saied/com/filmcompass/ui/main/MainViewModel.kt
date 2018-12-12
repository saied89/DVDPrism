package android.saied.com.filmcompass.ui.main

import android.saied.com.filmcompass.MovieRepository
import android.saied.com.filmcompass.model.Movie
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Try
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(val movieRepo: MovieRepository) : ViewModel() {

}