package com.example.ionage.network

import android.app.appsearch.SearchResult
import com.example.ionage.util.ApiResponse


interface ApiInterface {
    fun search(text: String): ApiResponse<SearchResult> {
        //TODO
    }

}