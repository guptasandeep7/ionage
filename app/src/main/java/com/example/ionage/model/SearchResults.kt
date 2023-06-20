package com.example.ionage.model



data class SearchResults(
    val Search : List<SearchResult>,
    val totalResults:Int,
    val Response:Boolean
)
