package com.example.collageapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.collageapp.common.Common
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_place.*

class ViewPlaceActivity : AppCompatActivity() {

    private val mViewModel by lazy {
        ViewModelProvider(this).get(ViewPlaceViewModel::class.java)
    }
    private var url:String?=null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_place)
        place_name.text = ""
        place_address.text = ""
        place_open_hour.text = ""


        mViewModel.getPlaceDetail()
        mViewModel.mPlace.observe(this, Observer {

            place_address.text = it.result!!.formattedAddress
            place_name.text = it!!.result!!.name
            url=it.result!!.url
        })


        mViewModel.getPhotoOfPlace(Common.currentResult!!.photos!![0].photo_reference!!, 1000)
        if (Common.currentResult!!.photos != null && Common.currentResult!!.photos!!.isNotEmpty()) {
            mViewModel.photoUrl.observe(this, Observer {
                Picasso.with(this)
                    .load(it)
                    .into(photo)
            })

        }
        if (Common.currentResult!!.rating != null) {
            rating_bar.rating = Common.currentResult!!.rating!!.toFloat()
        }
        else
            rating_bar.visibility= View.GONE
        if (Common.currentResult!!.openingHours != null) {
            place_open_hour.text = "Open now: "+Common.currentResult!!.openingHours!!.open_now
        }
        else
            place_open_hour.visibility = View.GONE

        btn_show_map.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(mapIntent)
        }
    }





}
