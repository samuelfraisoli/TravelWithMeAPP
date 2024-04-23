package com.example.travelwithmeapp.modelsDTO

data class DetallesHotelDTO(
    var id: Long = -1,
    var descripcion: String = "",
    var web: String = "",
    var telefono: String = "",
    var comodidades: ArrayList<String> = ArrayList()
)
