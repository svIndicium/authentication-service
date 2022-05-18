package hu.indicium.dev.services.authentication.credentials

interface PasswordEncoder {
    fun encode(password: String): String

    fun matches(password: String, encodedPassword: String): Boolean
}