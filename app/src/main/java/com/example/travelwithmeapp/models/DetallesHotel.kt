package com.example.travelwithmeapp.models

data class DetallesHotel(
    var id: Long = -1,
    var descripcion: String = "",
    var web: String = "",
    var telefono: String = "",
    var comodidades: ArrayList<String> = ArrayList(),
    var precio: Double = 0.0
)
