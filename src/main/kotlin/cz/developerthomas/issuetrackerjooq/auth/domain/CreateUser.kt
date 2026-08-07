package cz.developerthomas.issuetrackerjooq.auth.domain

import cz.developerthomas.issuetrackerjooq.user.domain.UserId

data class CreateUser(
    val id: UserId,
    val email: String,
    val name: String,
    val jobTitle: String? = null,
    val authId: AuthId,
)