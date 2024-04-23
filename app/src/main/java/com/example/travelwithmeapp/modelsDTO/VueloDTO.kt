package com.example.travelwithmeapp.modelsDTO

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Date
import java.io.Serializable
import java.time.Duration
import java.time.OffsetDateTime

/**
 * id -> la id con la que lo guardamos en la BD
 * id_vuelo -> la id que le da la aerolínea (ej. C3451)
 * tipo -> turista, business, etc
 *
 */
data class VueloDTO(
    var id: Long = -1,
    var id_vuelo: String = "",
    var aerolinea: String = "",
    var precio: Double = 0.0,
    var tipo: String = "turista",
    var origen_id: Long = -1,
    var destino_id: Long = -1,
    var fecha: OffsetDateTime = OffsetDateTime.now(),
    var equipaje_id: Long = -1
) : Serializable {


}




