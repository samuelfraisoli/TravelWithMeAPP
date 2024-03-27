package com.example.travelwithmeapp.models
import java.io.Serializable

data class User(
    var uid: String = "",
    var email: String = "",
    var provider: ProviderType = ProviderType.NULL,
    var name: String = "",
    var surname: String = "",
    var birthdate: String = "",
    var telephone: String = "",

    ) : Serializable

