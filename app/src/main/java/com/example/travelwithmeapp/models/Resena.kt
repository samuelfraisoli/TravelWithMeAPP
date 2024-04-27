package com.example.travelwithmeapp.models

import java.io.Serializable
import java.time.OffsetDateTime
import java.util.Date

data class Resena(
    var id: Long = -1,
    val idUsuario: Long = -1,
    val fecha: OffsetDateTime = OffsetDateTime.now(),
    val titulo: String = "",
    val contenido: String = "",
    val nota: Int = -1
    ) : Serializable
