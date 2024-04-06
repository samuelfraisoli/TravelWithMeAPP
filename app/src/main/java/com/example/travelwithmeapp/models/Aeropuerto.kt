package com.example.travelwithmeapp.models

import java.io.Serializable

class Aeropuerto (
    var id: String,
    var nombre: String,
    var ciudad: String,
    var ciudadAbrev: String,
    var pais: String
) : Serializable