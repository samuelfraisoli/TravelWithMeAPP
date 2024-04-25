package com.example.travelwithmeapp.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * CiudadAbrev -> las letras que tiene cada ciudad (ej. Madrid: MDR)
 */
data class Aeropuerto(
    var id: Long = -1,
    var nombre: String = "",
    var ciudad: String = "",
    var ciudadAbrev: String = "",
    var pais: String = "",
) : Serializable
