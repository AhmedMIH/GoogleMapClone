package com.example.collageapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.collageapp.common.Common
import com.example.collageapp.model.PlaceDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPlaceViewModel:ViewModel() {
    private val mService = Common.googleApiService

    val mPlace = MutableLiveData<PlaceDetails>()
    val photoUrl=MutableLiveData<String>()
    fun getPlaceDetail(){
        mService.getDetailPlace(getPlaceUrl(Common.currentResult!!.placeId!!))
            .enqueue(object : Callback<PlaceDetails>{
                override fun onFailure(call: Call<PlaceDetails>, t: Throwable) {
                    Log.e("error",""+t.message)
                }

                override fun onResponse(call: Call<PlaceDetails>, response: Response<PlaceDetails>)
                {
                    mPlace.value = response.body()!!
                    Log.d("check",""+mPlace)
                }

            })
    }

    private fun getPlaceUrl(placeId: String): String {
        val url  = StringBuilder("https://maps.googleapis.com/maps/api/place/details/json")
            .append("?place_id=$placeId")
            .append("&key=AIzaSyDmThyFhWGxSgY3TiW42-0Lp-8icoSSBjY")

        Log.d("check",placeId)
        Log.d("check",url.toString())
        return url.toString()
    }

     fun getPhotoOfPlace(photoReference: String, i: Int) {
        val url  = StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
            .append("?maxwidth=$i")
            .append("&photorefernce=$photoReference")
            .append("&key=AIzaSyDmThyFhWGxSgY3TiW42-0Lp-8icoSSBjY")
        Log.d("photo",""+url.toString())
        photoUrl.value=url.toString()

    }
}