package com.example.ionage.network


import com.example.ionage.model.SearchResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("/?")
    fun search(
        @Query("s") text: String,
        @Query("apikey") apikey: String
    ): Call<SearchResults>


}