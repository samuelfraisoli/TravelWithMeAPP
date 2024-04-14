package com.example.travelwithmeapp.models

import java.util.Date
import java.io.Serializable
import kotlin.time.Duration

data class Vuelo(
    var id: String = "",
    var aerolinea: String = "",
    var origen: Aeropuerto = Aeropuerto(),
    var destino: Aeropuerto = Aeropuerto(),
    var fechaSalida: Date = Date(),
    var fechaLlegada: Date = Date(),
    var duracion: Duration = Duration.ZERO,
    var trayectos: ArrayList<TrayectoVuelo> = ArrayList(),
    var precio: Double = 0.0,
    var tipo: String = "turista"
) : Serializable