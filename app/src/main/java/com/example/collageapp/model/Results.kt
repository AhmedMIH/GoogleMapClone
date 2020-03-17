package com.example.collageapp.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Results :Serializable{

    @SerializedName("address_components")
    @Expose
    var addressComponents: List<AddressComponent>? = null

    @SerializedName("adr_address")
    @Expose
    var adrAddress: String? = null

    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String? = null

    @SerializedName("formatted_phone_number")
    @Expose
    var formattedPhoneNumber: String? = null

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("international_phone_number")
    @Expose
    var internationalPhoneNumber: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("opening_hours")
    @Expose
    var openingHours: OpeningHours? = null

    @SerializedName("photos")
    @Expose
    var photos: List<Photo>? = null

    @SerializedName("place_id")
    @Expose
    var placeId: String? = null

    @SerializedName("plus_code")
    @Expose
    var plusCode: PlusCode? = null

    @SerializedName("rating")
    @Expose
    var rating: Float? = null

    @SerializedName("reference")
    @Expose
    var reference: String? = null

    @SerializedName("reviews")
    @Expose
    var reviews: List<Review>? = null

    @SerializedName("scope")
    @Expose
    var scope: String? = null

    @SerializedName("types")
    @Expose
    var types: List<String>? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("user_ratings_total")
    @Expose
    var userRatingsTotal: Int? = null

    @SerializedName("utc_offset")
    @Expose
    var utcOffset: Int? = null

    @SerializedName("vicinity")
    @Expose
    var vicinity: String? = null

    @SerializedName("website")
    @Expose
    var website: String? = null
}
class Review:Serializable {
    @SerializedName("author_name")
    @Expose
    var authorName: String? = null

    @SerializedName("author_url")
    @Expose
    var authorUrl: String? = null

    @SerializedName("language")
    @Expose
    var language: String? = null

    @SerializedName("profile_photo_url")
    @Expose
    var profilePhotoUrl: String? = null

    @SerializedName("rating")
    @Expose
    var rating: Int? = null

    @SerializedName("relative_time_description")
    @Expose
    var relativeTimeDescription: String? = null

    @SerializedName("text")
    @Expose
    var text: String? = null

    @SerializedName("time")
    @Expose
    var time: Int? = null
}
class PlusCode:Serializable {
    @SerializedName("compound_code")
    @Expose
    var compoundCode: String? = null

    @SerializedName("global_code")
    @Expose
    var globalCode: String? = null
}
class Period:Serializable {
    @SerializedName("close")
    @Expose
    var close: Close? = null

    @SerializedName("open")
    @Expose
    var open: Open? = null
}
class Open:Serializable {
    @SerializedName("day")
    @Expose
    var day: Int? = null

    @SerializedName("time")
    @Expose
    var time: String? = null
}
class AddressComponent :Serializable{
    @SerializedName("long_name")
    @Expose
    var longName: String? = null

    @SerializedName("short_name")
    @Expose
    var shortName: String? = null

    @SerializedName("types")
    @Expose
    var types: List<String>? = null
}
class Close :Serializable{
    @SerializedName("day")
    @Expose
    var day: Int? = null

    @SerializedName("time")
    @Expose
    var time: String? = null
}