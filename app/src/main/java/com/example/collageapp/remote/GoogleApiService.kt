package com.example.collageapp.remote

import com.example.collageapp.model.MyPlace
import com.example.collageapp.model.PlaceDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleApiService {
    @GET
    fun getNearByPlaces(@Url url: String):Call<MyPlace>

    @GET
    fun getDetailPlace(@Url url: String):Call<PlaceDetails>
}