package com.example.collageapp.model

import java.io.Serializable

class MyPlace : Serializable {

    var html_attributions :Array<String> ?= null
    var status:String ?= null
    var next_page_token:String ?= null
    var results:Array<Results> ?= null
}