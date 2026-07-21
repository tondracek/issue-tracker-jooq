package cz.developerthomas.issuetrackerjooq.auth.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.servlet.function.router

@Configuration
class AuthRouter {

    @Bean
    fun authRoutes() = router {
        "/auth".nest {
            GET("/me") { request ->
                val jwt = request.principal().orElseThrow() as JwtAuthenticationToken

                ok().body(jwt.token.claims)
            }
        }
    }
}
