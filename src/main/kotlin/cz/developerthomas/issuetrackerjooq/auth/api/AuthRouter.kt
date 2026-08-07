package cz.developerthomas.issuetrackerjooq.auth.api

import cz.developerthomas.issuetrackerjooq.auth.dto.RegisterRequest
import cz.developerthomas.issuetrackerjooq.auth.usecase.RegisterUserUC
import cz.developerthomas.issuetrackerjooq.core.validation.validBody
import jakarta.validation.Validator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.servlet.function.router

@Configuration
class AuthRouter(
    private val registerUserUC: RegisterUserUC,
    private val validator: Validator,
) {

    @Bean
    fun authRoutes() = router {
        "/auth".nest {
            GET("/me") { request ->
                val jwt = request.principal().orElseThrow() as JwtAuthenticationToken

                ok().body(jwt.token.claims)
            }

            POST("/register") { request ->
                val request = request.validBody<RegisterRequest>(validator)

                val id = registerUserUC(request)

                ok().body(id)
            }
        }
    }
}
