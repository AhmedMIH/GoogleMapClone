package com.example.collageapp.model

import java.io.Serializable


class Geometry: Serializable {
    var viewport:Viewport?=null
    var location: Location?=null
}

class  Viewport :Serializable{
    var northeast:Northeast?=null
    var southeast:Southeast?=null

}

class Southeast :Serializable{
    var lat:Double=0.0
    var lng:Double=0.0
}

class Northeast :Serializable{
    var lat:Double=0.0
    var lng:Double=0.0
}


class Location :Serializable{
    var lat:Double=0.0
    var lng:Double=0.0
}
