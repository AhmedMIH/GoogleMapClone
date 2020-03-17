package com.example.collageapp.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class OpeningHours : Serializable {
    var open_now:Boolean=false
    var weekday_text:Array<String>?=null

    @SerializedName("periods")
    @Expose
    var periods: List<Period>? = null

}
