package com.example.travelwithmeapp.modelsDTO

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import kotlin.time.Duration

/**
 * id -> la id con la que lo guardamos en la BD
 * id_trayecto -> la id que le da la aerolínea (ej. C3451)
 */
data class TrayectoVueloDTO @RequiresApi(Build.VERSION_CODES.O) constructor(
    var id: Long = -1,
    var id_trayecto: String = "",
    var aerolinea: String = "",
    var tipo: String = "",
    var fechaSalida: OffsetDateTime = OffsetDateTime.now(),
    var fechaLlegada: OffsetDateTime = OffsetDateTime.now(),
    var escala: Boolean = false,
    var fechaInicioEscala: OffsetDateTime = OffsetDateTime.now(),
    var fechaFinEscala: OffsetDateTime = OffsetDateTime.now(),
    var terminalSalida: String = "",
    var terminalLlegada: String = "",
    var vuelo_id: Long = -1,
    var origen_aeropuerto_id: Long = -1,
    var destino_aeropuerto_id: Long = -1,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDuracion(): java.time.Duration {
        return java.time.Duration.between(fechaSalida, fechaLlegada)
    }


    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDuracionEscalaHHhMMm(): String {
        val duracion = getDuracionEscala()
        val horas = duracion.toHours()
        val minutosRestantes = duracion.minusHours(horas).toMinutes()
        return "${horas}h ${minutosRestantes}m"
    }
}
