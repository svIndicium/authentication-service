package hu.indicium.dev.services.authentication.credentials

import com.kosprov.jargon2.api.Jargon2.*
import hu.indicium.dev.services.authentication.config.AuthenticationConfig
import jakarta.inject.Singleton

@Singleton
class Argon2PasswordEncoder(
    authenticationConfig: AuthenticationConfig
) : PasswordEncoder {

    private val hasher = jargon2Hasher()
        .type(Type.ARGON2id)
        .memoryCost(authenticationConfig.argon2?.memory ?: 65536)
        .timeCost(authenticationConfig.argon2?.iterations ?: 10)
        .parallelism(authenticationConfig.argon2?.parallelism ?: 1)

    private val verifier = jargon2Verifier()

    override fun encode(password: String): String =
        toByteArray(password).use { passwordByteArray ->
            hasher.password(passwordByteArray).encodedHash()
        }

    override fun matches(password: String, encodedPassword: String): Boolean =
        toByteArray(password).use { passwordByteArray ->
            verifier.hash(encodedPassword).password(passwordByteArray).verifyEncoded()
        }
}