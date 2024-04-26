package com.example.travelwithmeapp.models

data class HotelDetails(
    var description: String = "",
    var web: String = "",
    var telephone: String = "",
    var amenities: ArrayList<String> = ArrayList()
)
