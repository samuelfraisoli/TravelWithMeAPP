package com.example.travelwithmeapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import kotlin.time.Duration

/**
 * id -> la id con la que lo guardamos en la BD
 * id_trayecto -> la id que le da la aerolínea (ej. C3451)
 */
data class TrayectoVuelo(
    var id: Long = -1,
    var id_trayecto: String = "",
    var aerolinea: String = "",
    var terminalSalida: String = "",
    var terminalLlegada: String = "",
    var fechaSalida: OffsetDateTime = OffsetDateTime.now(),
    var fechaLlegada: OffsetDateTime = OffsetDateTime.now(),
    var escala: Boolean = false,
    var fechaInicioEscala: OffsetDateTime = OffsetDateTime.now(),
    var fechaFinEscala: OffsetDateTime = OffsetDateTime.now(),

    var origen: Aeropuerto = Aeropuerto(),
    var destino: Aeropuerto = Aeropuerto(),
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDuracion(): java.time.Duration {
        return java.time.Duration.between(fechaSalida, fechaLlegada)
    }


    fun getDuracionFormatoHHhMMm(): String {
        val duracion = getDuracion()
        val horas = duracion.toHours()
        val minutosRestantes = duracion.minusHours(horas).toMinutes()
        return "${horas}h ${minutosRestantes}m"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDuracionEscala() : java.time.Duration {
        return java.time.Duration.between(fechaInicioEscala, fechaFinEscala)

    }

    fun getDuracionEscalaHHhMMm(): String {
        val duracion = getDuracionEscala()
        val horas = duracion.toHours()
        val minutosRestantes = duracion.minusHours(horas).toMinutes()
        return "${horas}h ${minutosRestantes}m"
    }
}
