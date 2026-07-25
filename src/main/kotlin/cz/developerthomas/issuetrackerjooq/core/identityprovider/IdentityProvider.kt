package cz.developerthomas.issuetrackerjooq.core.identityprovider

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId

interface IdentityProvider {

    fun createUser(email: String, password: String): AuthId

    fun deleteUser(authId: AuthId)
}