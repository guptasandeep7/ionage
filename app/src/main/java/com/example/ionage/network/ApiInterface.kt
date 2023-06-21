package com.example.ionage.network


import com.example.ionage.model.SearchResult
import com.example.ionage.model.SearchResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("/?")
    fun search(
        @Query("s") text: String,
        @Query("page") pageno: Int,
        @Query("apikey") apikey: String
    ): Call<SearchResults>

    @GET("/?")
    fun getMovieDetails(
        @Query("i") text: String,
        @Query("apikey") token: String
    ): Call<SearchResult>

}