package com.example.travelwithmeapp.models
import java.io.Serializable


data class Hotel (
    var id: String = "",
    var name: String = "",
    var address: Address = Address(),
    var photos: HotelPhotos = HotelPhotos(),
    var details: HotelDetails = HotelDetails()


) : Serializable