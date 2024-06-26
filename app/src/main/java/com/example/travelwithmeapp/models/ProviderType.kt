package com.example.travelwithmeapp.models

/**
 * Enum class representing different authentication providers.
 *
 * This enum class defines three possible values:
 * - NULL: Default value indicating no specific provider.
 * - BASIC: Represents basic email/password authentication.
 * - GOOGLE: Represents authentication via Google Sign-In.
 */

enum class ProviderType {
    NULL,
    BASIC,
    GOOGLE
}