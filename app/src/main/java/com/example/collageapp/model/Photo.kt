package com.example.collageapp.model

import java.io.Serializable

class Photo : Serializable {
    var height:Int=0
    var width:Int=0
    var html_attributions:Array<String>?=null
    var photo_reference:String?=null

}