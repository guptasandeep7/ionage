package com.example.ionage.viewmodel

import android.app.appsearch.SearchResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ionage.repo.SearchRepo
import com.example.ionage.util.ApiResponse
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private var _searchData: MutableLiveData<ApiResponse<SearchResult>> = MutableLiveData()

    fun getSearch(text: String): MutableLiveData<ApiResponse<SearchResult>> {
        viewModelScope.launch {
            _searchData = SearchRepo().postSearch(text)
        }
        return _searchData
    }
}