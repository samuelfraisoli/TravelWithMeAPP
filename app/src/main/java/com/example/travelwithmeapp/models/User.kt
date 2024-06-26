package com.example.travelwithmeapp.models
import java.io.Serializable

/**
 * Data class representing user information.
 *
 * @property uid Unique identifier of the user.
 * @property email Email address of the user.
 * @property provider Provider type used for authentication (e.g., BASIC, GOOGLE).
 * @property name First name of the user.
 * @property surname Last name of the user.
 * @property birthdate Date of birth of the user.
 * @property telephone Telephone number of the user.
 *
 * @author Samuel Fraisoli
 */

data class User(
    var uid: String = "",
    var email: String = "",
    var provider: ProviderType = ProviderType.NULL,
    var name: String = "",
    var surname: String = "",
    var birthdate: String = "",
    var telephone: String = "",

    ) : Serializable

