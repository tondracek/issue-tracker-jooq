package cz.developerthomas.issuetrackerjooq.user.domain

import cz.developerthomas.issuetrackerjooq.auth.domain.AuthId
import cz.developerthomas.issuetrackerjooq.enums.AppRole

data class CurrentUser(
    val id: UserId,
    val authId: AuthId,
    val role: AppRole,
)