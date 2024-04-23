package com.example.travelwithmeapp.modelsDTO

import java.io.Serializable

/**
 * direccionString -> La direccion completa en String ej. Calle cordel de Pavones, 1, 1ºB Madrid, España, 28001
 * direccion1 -> La calle y numero ej. Calle cordel de Pavones, 1
 * direccion2 -> Portal y piso, escalera, etc. Se puede dejar vacía
 */
data class DireccionDTO(
    var id: Long = -1,
    var direccionString: String = "",
    var direccion1: String = "",
    var direccion2: String = "",
    var ciudad: String = "",
    var pais: String = "",
    var codPostal: String = "",
) : Serializable



