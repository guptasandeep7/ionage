package com.example.ionage.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASEURL = "http://www.omdbapi.com"
    fun buildService(token: String): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)
    }
}
