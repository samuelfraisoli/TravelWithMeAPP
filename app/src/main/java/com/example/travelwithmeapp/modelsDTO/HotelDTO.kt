package com.example.travelwithmeapp.modelsDTO
import java.io.Serializable
import java.time.OffsetDateTime
import java.util.Date


data class HotelDTO (
    var id: Long = -1,
    var nombre: String = "",
    var fotos: ArrayList<String> = ArrayList(),
    var fechasLibres: ArrayList<OffsetDateTime> = ArrayList(),
    var direccion_id: Long = -1,
    var detalles_hotel_id: Long = -1,

) : Serializable