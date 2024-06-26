package com.example.travelwithmeapp.models
import java.io.Serializable
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.Date

/**
 * Data class representing hotel details.
 *
 * @property id The unique identifier of the hotel.
 * @property nombre The name of the hotel.
 * @property fotos List of URLs to hotel photos.
 * @property fechasLibres List of available dates for booking.
 * @property direccion The address details of the hotel.
 * @property detalles The additional details of the hotel.
 * @property resenas List of reviews for the hotel.
 */

data class Hotel (
    var id: Long = -1,
    var nombre: String = "",
    var fotos: ArrayList<String> = ArrayList(),
    var fechasLibres: ArrayList<LocalDate> = ArrayList(),
    var direccion: Direccion = Direccion(),
    var detalles: DetallesHotel = DetallesHotel(),
    var resenas: ArrayList<Resena> = ArrayList()
) : Serializable