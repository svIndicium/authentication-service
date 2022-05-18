package hu.indicium.dev.services.authentication.config

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("authentication")
class AuthenticationConfig {
    var argon2: Argon2Config? = null

    @ConfigurationProperties("argon2")
    class Argon2Config {
        var iterations: Int = 10
        var memory: Int = 65536
        var parallelism: Int = 1
    }
}