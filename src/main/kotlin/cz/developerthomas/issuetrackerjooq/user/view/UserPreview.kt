package cz.developerthomas.issuetrackerjooq.user.view

import cz.developerthomas.issuetrackerjooq.user.domain.UserId

data class UserPreview(
    val id: UserId,
    val name: String,
    val jobTitle: String? = null,
)
