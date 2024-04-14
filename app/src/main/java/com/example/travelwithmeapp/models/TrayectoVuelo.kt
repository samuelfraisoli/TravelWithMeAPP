package com.example.travelwithmeapp.models

import java.util.Date
import kotlin.time.Duration

data class TrayectoVuelo(
    var id: String = "",
    var origen: Aeropuerto = Aeropuerto(),
    var destino: Aeropuerto = Aeropuerto(),
    var fechaSalida: Date = Date(),
    var fechaLlegada: Date = Date(),
    var duracion: Duration = Duration.ZERO,
    var aerolinea: String = "",
    var escala: Boolean = false,
    var tiempoEscala: Duration = Duration.ZERO
)