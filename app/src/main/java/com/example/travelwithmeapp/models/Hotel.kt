package com.example.travelwithmeapp.models
import java.io.Serializable


data class Hotel (
    var id: String = "",
    var name: String = "",
    var address: Address = Address(),
    var photos: ArrayList<String> = ArrayList(),
    var details: HotelDetails = HotelDetails(),
    var reviews: ArrayList<String> = ArrayList()


) : Serializable