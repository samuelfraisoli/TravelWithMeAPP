package com.example.travelwithmeapp.models
import java.io.Serializable
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.Date


data class Hotel (
    var id: Long = -1,
    var nombre: String = "",
    var fotos: ArrayList<String> = ArrayList(),
    var fechasLibres: ArrayList<LocalDate> = ArrayList(),
    var direccion: Direccion = Direccion(),
    var detalles: DetallesHotel = DetallesHotel(),
    var resena: ArrayList<Resena> = ArrayList()
) : Serializable