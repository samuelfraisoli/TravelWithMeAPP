package com.example.travelwithmeapp.models
import java.io.Serializable
import java.util.Date


data class Hotel (
    var id: String = "",
    var name: String = "",
    var address: Address = Address(),
    var photos: ArrayList<String> = ArrayList(),
    var details: HotelDetails = HotelDetails(),
    var reviews: ArrayList<String> = ArrayList(),
    var fechasLibres: ArrayList<Date> = ArrayList()


) : Serializable