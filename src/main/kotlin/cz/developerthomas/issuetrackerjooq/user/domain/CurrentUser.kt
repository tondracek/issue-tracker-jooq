package cz.developerthomas.issuetrackerjooq.user.domain

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId

data class CurrentUser(
    val id: UserId,
    val authId: AuthId,
)