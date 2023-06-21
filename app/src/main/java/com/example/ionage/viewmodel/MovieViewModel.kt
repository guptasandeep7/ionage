package com.example.ionage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ionage.model.SearchResult
import com.example.ionage.repo.MovieRepo
import com.example.ionage.util.ApiResponse
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private var _movieData: MutableLiveData<ApiResponse<SearchResult>> = MutableLiveData()


    fun getMovieDetails(text: String): MutableLiveData<ApiResponse<SearchResult>> {
        viewModelScope.launch {
            _movieData = MovieRepo().getMovieDetails(text)
        }
        return _movieData
    }
}