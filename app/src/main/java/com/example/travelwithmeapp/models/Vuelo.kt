package com.example.travelwithmeapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.Duration
import java.time.OffsetDateTime

/**
 * Data class representing flight information.
 *
 * @property id Unique identifier of the flight (in our database).
 * @property id_vuelo Flight identifier string given by the airline.
 * @property aerolinea Airline operating the flight.
 * @property precio Price of the flight.
 * @property tipo Type of flight (e.g., turista, business).
 * @property origen Departure location of the flight.
 * @property destino Destination location of the flight.
 * @property fecha Departure date and time of the flight.
 * @property trayectos List of flight segments or legs.
 * @property equipaje Details of the baggage allowance for the flight.
 *
 * @author Samuel Fraisoli
 */

data class Vuelo(
    var id: Long = -1,
    var id_vuelo: String = "",
    var aerolinea: String = "",
    var precio: Double = 0.0,
    var tipo: String = "turista",
    var origen: String = "",
    var destino: String = "",
    var fecha: OffsetDateTime = OffsetDateTime.now(),

    var trayectos: ArrayList<TrayectoVuelo> = ArrayList(),
    var equipaje: Equipaje = Equipaje()
) : Serializable {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDuracionTotal(): Duration {
        var duracionTotal = Duration.ZERO
        for (trayecto in trayectos) {
            val duracionTrayecto = Duration.between(trayecto.fechaSalida, trayecto.fechaLlegada)
            duracionTotal = duracionTotal.plus(duracionTrayecto)
        }
        return duracionTotal
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDuracionTotalFormatoHHhMMm() : String {
        val duracion = getDuracionTotal()
        val horas = duracion.toHours()
        val minutosRestantes = duracion.minusHours(horas).toMinutes()
        return "${horas}h ${minutosRestantes}m"
    }


    fun getAeropuertoOrigen() : Aeropuerto {
        if (!trayectos.isNullOrEmpty()) {
            return trayectos.get(0).origen
        }
        return Aeropuerto()
    }

    fun getAeropuertoDestino() : Aeropuerto {
        if(!trayectos.isNullOrEmpty()) {
            return trayectos.get(trayectos.size - 1).destino
        }
        return Aeropuerto()
    }

    fun getPrimerTrayecto() : TrayectoVuelo {
        if (!trayectos.isNullOrEmpty()) {
            return trayectos.get(0)
        }
        return TrayectoVuelo()
    }

    fun getUltimoTrayecto() : TrayectoVuelo {
        if(!trayectos.isNullOrEmpty()) {
            return trayectos.get(trayectos.size - 1)
        }
        return TrayectoVuelo()
    }

    fun getNumeroEscalas() : String {
        if(trayectos.size == 1 || trayectos.size == 0) {
            return "Sin escalas" }
        else if(trayectos.size == 2) {
            return "1 escala" }
        else { return "${trayectos.size + 1} escalas" }
    }
}




