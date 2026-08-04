package cz.developerthomas.issuetrackerjooq.audit.domain

import cz.developerthomas.issuetrackerjooq.enums.AuditEvent
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import java.util.*

data class CreateAuditLogCommand(
    val id: AuditLogId,
    val entityId: UUID,
    val displayName: String?,
    val authorId: UserId,
    val action: AuditEvent,
    val payload: AuditPayload,
)

