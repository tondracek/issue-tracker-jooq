package cz.developerthomas.issuetrackerjooq.core.identityprovider.keycloak

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig {

    @Bean
    fun keycloak(properties: KeycloakProperties): Keycloak =
        KeycloakBuilder.builder()
            .serverUrl(properties.serverUrl)
            .realm(properties.realm)
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .clientId(properties.clientId)
            .clientSecret(properties.clientSecret)
            .build()
}