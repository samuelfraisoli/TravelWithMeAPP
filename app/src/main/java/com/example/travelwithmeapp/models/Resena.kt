package com.example.travelwithmeapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.OffsetDateTime

/**
 * Data class representing a review.
 *
 * @property id The unique identifier of the review.
 * @property idUsuario The user ID associated with the review.
 * @property fecha The date and time when the review was created.
 * @property titulo The title of the review.
 * @property contenido The content or text of the review.
 * @property nota The rating given in the review, default is 1.0.
 */

data class Resena @RequiresApi(Build.VERSION_CODES.O) constructor(
    var id: String = "",
    var idUsuario: String = "",
    var fecha: OffsetDateTime = OffsetDateTime.now(),
    var titulo: String = "",
    var contenido: String = "",
    var nota: Float = 1.0F
    ) : Serializable
