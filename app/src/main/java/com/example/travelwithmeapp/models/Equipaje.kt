package com.example.travelwithmeapp.models

import java.io.Serializable

/**
 * Data class representing luggage details.
 *
 * @property id The unique identifier of the luggage.
 * @property peso The weight of the luggage.
 * @property alto The height of the luggage.
 * @property ancho The width of the luggage.
 * @property precio The price associated with the luggage.
 *
 * @author Samuel Fraisoli
 */

data class Equipaje(
    var id: Long = -1,
    var peso: String = "",
    var alto: String = "",
    var ancho: String = "",
    var precio: String = "",
) : Serializable