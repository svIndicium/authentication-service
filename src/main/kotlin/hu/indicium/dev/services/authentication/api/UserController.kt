package hu.indicium.dev.services.authentication.api

import hu.indicium.dev.services.authentication.credentials.PasswordEncoder
import hu.indicium.dev.services.authentication.model.User
import hu.indicium.dev.services.authentication.persistency.UserRepository
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Controller("/user")
@Secured(SecurityRule.IS_ANONYMOUS)
class UserController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Post
    suspend fun createUser(@Body user: User?): User? =
        user?.let {
            userRepository.save(it.copy(
                password = passwordEncoder.encode(it.password ?: "test")
            ))
        }
}
