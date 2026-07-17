package cz.developerthomas.issuetrackerjooq.user.view

import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import java.time.LocalDateTime

data class UserDetailView(
    val id: UserId,
    val email: String,
    val name: String,
    val jobTitle: String?,
    val createdAt: LocalDateTime,
)


