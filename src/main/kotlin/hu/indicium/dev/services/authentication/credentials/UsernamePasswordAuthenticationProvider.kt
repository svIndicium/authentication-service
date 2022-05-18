package hu.indicium.dev.services.authentication.credentials

import hu.indicium.dev.services.authentication.model.User
import hu.indicium.dev.services.authentication.persistency.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.Flowable
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.future.future

@Singleton
class UsernamePasswordAuthenticationProvider(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) : AuthenticationProvider {
    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Flowable<AuthenticationResponse> =
        Flowable.fromFuture(
            CoroutineScope(Default).future {
                val username = authenticationRequest?.identity?.toString() ?: return@future AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND)
                val secret = authenticationRequest.secret?.toString() ?: return@future AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND)
                val user = userRepository.findByUsername(username) ?: return@future AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND)
                user.password ?: return@future AuthenticationResponse.failure(AuthenticationFailureReason.PASSWORD_EXPIRED)
                when (matchPassword(user, secret)) {
                    false -> return@future AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND)
                    true -> AuthenticationResponse.success(user.username, listOf("ADMIN"), hashMapOf())
                }
            }
        )

    private fun matchPassword(user: User, password: String): Boolean =
        user.password?.let { passwordEncoder.matches(password, it) } == true
}