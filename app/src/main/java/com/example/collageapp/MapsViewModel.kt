package com.example.collageapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collageapp.common.Common
import com.example.collageapp.model.MyPlace
import com.example.collageapp.remote.GoogleApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel:ViewModel(){
    private lateinit var mServices: GoogleApiService

    val currentPlace = MutableLiveData<MyPlace>()

    private var type = MutableLiveData<String>()

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=1000000")//10km
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&keyword=cruise&key=AIzaSyDmThyFhWGxSgY3TiW42-0Lp-8icoSSBjY")

        Log.d("location",latitude.toString())

        Log.d("location",longitude.toString())
        Log.d("URL_DEBUG",googlePlaceUrl.toString())
        return googlePlaceUrl.toString()

    }

    fun getNearByPlaces(latitude: Double, longitude: Double, typePlace: String){
        type.value=typePlace
        mServices=Common.googleApiService
        mServices.getNearByPlaces(getUrl(latitude, longitude, typePlace))
            .enqueue(object : Callback<MyPlace> {
                override fun onFailure(call: Call<MyPlace>, t: Throwable) {
                    Log.d("viewHolder","Error")
                }

                override fun onResponse(call: Call<MyPlace>, response: Response<MyPlace>) {
                    if (response.isSuccessful) {
                        currentPlace.value = response.body()
                    }
                    else
                        Log.d("viewHolder",""+response.message())


                }
            })
    }
}


