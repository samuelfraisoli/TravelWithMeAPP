package com.example.travelwithmeapp.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Data class representing an airport.
 *
 * This class encapsulates basic information about an airport including its ID, name,
 * city, abbreviated city name, and country.
 *
 * @property id The ID of the airport.
 * @property nombre The name of the airport.
 * @property ciudad The city where the airport is located.
 * @property ciudadAbrev The abbreviated name of the city (Example: Madrid -> MDR).
 * @property pais The country where the airport is located.
 *
 * @constructor Creates an instance of Aeropuerto with default values.
 *
 * @author Samuel Fraisoli
 */

data class Aeropuerto(
    var id: Long = -1,
    var nombre: String = "",
    var ciudad: String = "",
    var ciudadAbrev: String = "",
    var pais: String = "",
) : Serializable
