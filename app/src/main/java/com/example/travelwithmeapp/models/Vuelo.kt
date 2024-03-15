package com.example.travelwithmeapp.models

import java.util.Date
import java.io.Serializable
data class Vuelo(
    var id: String,
    var aerolinea: String,
    var origen: Aeropuerto,
    var destino: Aeropuerto,
    var fechaSalida: Date,
    var fechaLlegada: Date,
    var precio: Double
) : Serializable