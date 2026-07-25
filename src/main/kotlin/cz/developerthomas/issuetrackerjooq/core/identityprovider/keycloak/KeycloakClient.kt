package cz.developerthomas.issuetrackerjooq.core.identityprovider.keycloak

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.core.identityprovider.IdentityProvider
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service


@Service
class KeycloakClient(
    private val keycloak: Keycloak,
    private val properties: KeycloakProperties,
) : IdentityProvider {

    private val users
        get() = keycloak.realm(properties.realm).users()

    override fun createUser(email: String, password: String): AuthId {
        val credential = CredentialRepresentation().apply {
            type = CredentialRepresentation.PASSWORD
            value = password
            isTemporary = false
        }

        val user = UserRepresentation().apply {
            username = email
            this.email = email
            isEnabled = true
            credentials = listOf(credential)
        }

        val response = users.create(user)

        if (response.status != 201) {
            error("Failed to create user: ${response.status}")
        }

        val id = CreatedResponseUtil.getCreatedId(response)

        return AuthId(id)
    }

    override fun deleteUser(authId: AuthId) {
        users.delete(authId.value)
    }
}
