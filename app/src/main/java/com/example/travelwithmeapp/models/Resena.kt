package com.example.travelwithmeapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.OffsetDateTime

data class Resena @RequiresApi(Build.VERSION_CODES.O) constructor(
    var id: String = "",
    var idUsuario: String = "",
    var fecha: OffsetDateTime = OffsetDateTime.now(),
    var titulo: String = "",
    var contenido: String = "",
    var nota: Float = 1.0F
    ) : Serializable
