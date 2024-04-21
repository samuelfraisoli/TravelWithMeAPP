package com.example.travelwithmeapp.models

data class DetallesHotel(
    var descripcion: String = "",
    var web: String = "",
    var telefono: String = "",
    var comodidades: ArrayList<String> = ArrayList()
)
