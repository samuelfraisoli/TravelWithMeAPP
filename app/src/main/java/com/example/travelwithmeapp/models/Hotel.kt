package com.example.travelwithmeapp.models

data class Hotel (
    var id: Long,
    var nombre: String,
    var descripcion: String,
    var direccion: String,
    var telefono: String,
    var web: String,
    //almacenamos las url de las imágenes
    var imagenes: ArrayList<String>

)