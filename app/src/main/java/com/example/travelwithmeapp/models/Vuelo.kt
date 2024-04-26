package com.example.travelwithmeapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.Duration
import java.time.OffsetDateTime

/**
 * id -> la id con la que lo guardamos en la BD
 * id_vuelo -> la id que le da la aerolínea (ej. C3451)
 * tipo -> turista, business, etc
 *
 */
data class Vuelo(
    var id: Long = -1,
    var id_vuelo: String = "",
    var aerolinea: String = "",
    var precio: Double = 0.0,
    var trayectos: ArrayList<TrayectoVuelo> = ArrayList(),
    var tipo: String = "turista",
    var origen: String = "",
    var destino: String = "",
    var fecha: OffsetDateTime = OffsetDateTime.now(),
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
        if(trayectos.size == 1 && trayectos.size == 0) {
            return "Sin escalas" }
        else if(trayectos.size == 2) {
            return "1 escala" }
        else { return "${trayectos.size + 1} escalas" }
    }
}




