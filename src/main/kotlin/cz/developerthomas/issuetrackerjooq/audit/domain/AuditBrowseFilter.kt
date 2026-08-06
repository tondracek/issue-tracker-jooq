package cz.developerthomas.issuetrackerjooq.audit.domain

import cz.developerthomas.issuetrackerjooq.enums.AuditEvent
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import java.util.*

data class AuditBrowseFilter(
    val entityId: UUID?,
    val authorId: UserId?,
    val action: AuditEvent?,
    //
    val offset: Int?,
    val limit: Int?,
)

