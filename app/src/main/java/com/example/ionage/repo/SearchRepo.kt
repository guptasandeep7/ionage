package com.example.ionage.repo


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ionage.model.SearchResults
import com.example.ionage.network.ServiceBuilder
import com.example.ionage.util.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepo {

    private val data = MutableLiveData<ApiResponse<SearchResults>>()

    suspend fun postSearch(
        text: String
    ): MutableLiveData<ApiResponse<SearchResults>> {

        val token = "b20ccbf5"
        val call = ServiceBuilder.buildService().search(text, token)

        data.postValue(ApiResponse.Loading())

        try {
            call.enqueue(object : Callback<SearchResults?> {
                override fun onResponse(
                    call: Call<SearchResults?>,
                    response: Response<SearchResults?>
                ) {
                    if (response.isSuccessful) {
                        Log.d("REPO", "onResponse: ${response.body()}")
                        data.postValue(ApiResponse.Success(response.body()))
                    } else {
                        data.postValue(ApiResponse.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<SearchResults?>, t: Throwable) {
                    data.postValue(ApiResponse.Error("Something went wrong!! ${t.message}"))
                }
            })
        } catch (e: java.lang.Exception) {
            data.postValue(ApiResponse.Error(e.message))
        }

        return data
    }
}