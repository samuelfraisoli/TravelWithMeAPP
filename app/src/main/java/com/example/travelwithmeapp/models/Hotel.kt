package com.example.travelwithmeapp.models
import java.io.Serializable


data class Hotel (
    var id: Long,
    var nombre: String,
    var descripcion: String,
    var direccion: String,
    var provincia: String,
    var pais: String,
    var telefono: String,
    var web: String,
    //almacenamos las url de las imágenes
    var imagenes: ArrayList<String>

) : Serializable