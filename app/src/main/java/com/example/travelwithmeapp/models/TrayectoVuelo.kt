package com.example.travelwithmeapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.OffsetDateTime

/**
 * Data class representing a flight segment or itinerary.
 *
 * @property id Unique identifier of the flight segment (in our database).
 * @property id_trayecto Identifier of the flight segment given by the airline.
 * @property aerolinea Airline associated with the flight segment.
 * @property tipo Type of flight segment (e.g., direct, connecting).
 * @property fechaSalida Departure date and time of the flight segment.
 * @property fechaLlegada Arrival date and time of the flight segment.
 * @property escala Indicates if there is a layover (true) or not (false).
 * @property fechaInicioEscala Start date and time of the layover.
 * @property fechaFinEscala End date and time of the layover.
 * @property terminalSalida Departure terminal of the flight segment.
 * @property terminalLlegada Arrival terminal of the flight segment.
 * @property origen Departure airport of the flight segment.
 * @property destino Arrival airport of the flight segment.
 *
 * @author Samuel Fraisoli
 */

data class TrayectoVuelo @RequiresApi(Build.VERSION_CODES.O) constructor(
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
    var origen: Aeropuerto = Aeropuerto(),
    var destino: Aeropuerto = Aeropuerto(),
) : Serializable {

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
