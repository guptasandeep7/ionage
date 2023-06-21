package com.example.ionage.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ionage.model.SearchResult
import com.example.ionage.network.ServiceBuilder
import com.example.ionage.util.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepo {

    private val data = MutableLiveData<ApiResponse<SearchResult>>()

    fun getMovieDetails(
        text: String
    ): MutableLiveData<ApiResponse<SearchResult>> {

        val token = "b20ccbf5"
        val call = ServiceBuilder.buildService().getMovieDetails(text, token)

        data.postValue(ApiResponse.Loading())

        try {
            call.enqueue(object : Callback<SearchResult?> {
                override fun onResponse(
                    call: Call<SearchResult?>,
                    response: Response<SearchResult?>
                ) {
                    if (response.isSuccessful) {
                        Log.d("REPO", "onResponse: ${response.body()}")
                        data.postValue(ApiResponse.Success(response.body()))
                    } else {
                        data.postValue(ApiResponse.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<SearchResult?>, t: Throwable) {
                    data.postValue(ApiResponse.Error("Something went wrong!! ${t.message}"))
                }
            })
        } catch (e: java.lang.Exception) {
            data.postValue(ApiResponse.Error(e.message))
        }

        return data
    }
}
