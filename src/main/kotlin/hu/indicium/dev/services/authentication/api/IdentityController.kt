package hu.indicium.dev.services.authentication.api

import hu.indicium.dev.rest.HttpResponse
import hu.indicium.dev.rest.HttpResponse.Builder.Companion.ok
import hu.indicium.dev.services.authentication.persistency.UserRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/identity")
class IdentityController(
    private val userRepository: UserRepository
) {

    @Get
    suspend fun getIdentity(authentication: Authentication): HttpResponse =
        userRepository.findByIdentifier(authentication.name).let { identity ->
            ok()
                .data(identity)
                .build()
        }

}