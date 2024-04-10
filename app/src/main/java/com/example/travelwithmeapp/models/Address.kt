package com.example.travelwithmeapp.models

import java.io.Serializable

data class Address(var addressString: String = "",
                   var street1: String = "",
                   var street2: String = "",
                   var city: String = "",
                   var country: String = "",
                   var postalCorde: String = "",
    ) : Serializable



