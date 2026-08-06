package cz.developerthomas.issuetrackerjooq.audit.domain

import cz.developerthomas.issuetrackerjooq.enums.AuditEvent
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import tools.jackson.databind.JsonNode
import java.util.*

data class CreateAuditLog(
    val id: AuditLogId,
    val entityId: UUID,
    val displayName: String?,
    val authorId: UserId,
    val action: AuditEvent,
    val payload: JsonNode,
)

