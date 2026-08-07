package cz.developerthomas.issuetrackerjooq.audit.view

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditLogId
import cz.developerthomas.issuetrackerjooq.enums.AuditEvent
import cz.developerthomas.issuetrackerjooq.user.view.UserPreview
import tools.jackson.databind.JsonNode
import java.time.LocalDateTime
import java.util.*

data class AuditLogListItemView(
    val id: AuditLogId,
    val entityId: UUID,
    val displayName: String?,
    val author: UserPreview,
    val action: AuditEvent,
    val payload: JsonNode,
    val createdAt: LocalDateTime,
)
