package com.example.ionage.repo

import android.app.appsearch.SearchResult
import androidx.lifecycle.MutableLiveData
import com.example.ionage.network.ApiInterface
import com.example.ionage.network.ServiceBuilder
import com.example.ionage.util.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class SearchRepo {

    private val data = MutableLiveData<ApiResponse<SearchResult>>()

    suspend fun postSearch(
        text: String
    ): MutableLiveData<ApiResponse<SearchResult>> {

        val token = "b20ccbf5"
        val call =ServiceBuilder.buildService(token).search(text)

        data.postValue(ApiResponse.Loading())


        return data
    }
}