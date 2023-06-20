package com.example.ionage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ionage.model.SearchResults
import com.example.ionage.repo.SearchRepo
import com.example.ionage.util.ApiResponse
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    var _searchData: MutableLiveData<ApiResponse<SearchResults>> = MutableLiveData()


    fun getSearch(text: String): MutableLiveData<ApiResponse<SearchResults>> {
        viewModelScope.launch {
            _searchData = SearchRepo().postSearch(text)
        }
        return _searchData
    }
}