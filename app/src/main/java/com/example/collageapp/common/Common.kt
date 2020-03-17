package com.example.collageapp.common

import com.example.collageapp.model.Results
import com.example.collageapp.remote.GoogleApiService
import com.example.collageapp.remote.RetrofitClient


object Common {
    private const val GOOGLE_API_URL="https://maps.googleapis.com/"

    var currentResult:Results?=null
    val googleApiService: GoogleApiService
        get() = RetrofitClient.getClient(GOOGLE_API_URL).create(GoogleApiService::class.java)


}