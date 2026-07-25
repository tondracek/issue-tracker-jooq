package cz.developerthomas.issuetrackerjooq.core.identityprovider.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("keycloak")
data class KeycloakProperties(
    val serverUrl: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String,
)