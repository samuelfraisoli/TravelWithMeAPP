package com.example.travelwithmeapp.models

import java.io.Serializable

/**
 * Data class representing details of a hotel.
 *
 * @property id The unique identifier of the hotel.
 * @property descripcion A brief description of the hotel.
 * @property web The website URL of the hotel.
 * @property telefono The contact phone number of the hotel.
 * @property comodidades List of amenities available at the hotel.
 * @property precio The price of staying at the hotel.
 */

data class DetallesHotel(
    var id: Long = -1,
    var descripcion: String = "",
    var web: String = "",
    var telefono: String = "",
    var comodidades: ArrayList<String> = ArrayList(),
    var precio: Double = 0.0
) : Serializable
