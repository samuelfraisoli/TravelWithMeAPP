package com.example.travelwithmeapp.models

import java.io.Serializable

/**
 * Data class representing an address.
 *
 * @property id The unique identifier of the address.
 * @property direccionString The full address as a single string.
 * @property direccion1 The first line of the address.
 * @property direccion2 The second line of the address.
 * @property ciudad The city of the address.
 * @property pais The country of the address.
 * @property codPostal The postal code of the address.
 * @property latitud The latitude coordinate of the address.
 * @property longitud The longitude coordinate of the address.
 *
 * @author Samuel Fraisoli
 */

data class Direccion(
    var id: Long = -1,
    var direccionString: String = "",
    var direccion1: String = "",
    var direccion2: String = "",
    var ciudad: String = "",
    var pais: String = "",
    var codPostal: String = "",
    var latitud: Double = 0.0,
    var longitud: Double = 0.0
) : Serializable



