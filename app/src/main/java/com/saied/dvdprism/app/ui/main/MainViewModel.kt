package com.saied.dvdprism.app.ui.main

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.app.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

abstract class MainViewModel(val movieRepository: MovieRepository) : ViewModel(){
    abstract val stateLiveData: MutableLiveData<MainState>
    abstract fun refreshMovies()
    abstract val latestLiveData: LiveData<PagedList<Movie>>
    abstract val upcommingLiveData: LiveData<PagedList<Movie>>
}