package com.example.travelwithmeapp.modelsDTO

import java.io.Serializable
import java.time.OffsetDateTime
import java.util.Date

data class ResenaDTO(
    var id: Long = -1,
    val idUsuario: Long = -1,
    val fecha: OffsetDateTime = OffsetDateTime.now(),
    val titulo: String = "",
    val contenido: String = "",
    val hotel_id: Long = -1
    ) : Serializable
