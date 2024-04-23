package com.example.travelwithmeapp.modelsDTO

import java.io.Serializable

/**
 * CiudadAbrev -> las letras que tiene cada ciudad (ej. Madrid: MDR)
 */
data class AeropuertoDTO (
    var id: Long = -1,
    var nombre: String = "",
    var ciudad: String = "",
    var ciudadAbrev: String = "",
    var pais: String = "",
) : Serializable