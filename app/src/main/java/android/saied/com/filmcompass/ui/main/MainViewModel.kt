package android.saied.com.filmcompass.ui.main

import android.saied.com.filmcompass.MovieRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(val movieRepo: MovieRepository) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val resLiveData = MutableLiveData<String>()

    fun fetchHtml(){
        uiScope.launch {
            resLiveData.value = movieRepo.getRottenTomatoes()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}