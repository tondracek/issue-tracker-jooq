package cz.developerthomas.issuetrackerjooq.audit.domain

import java.util.*

@JvmInline
value class AuditLogId(val value: UUID) {
    companion object {
        fun newId() = AuditLogId(UUID.randomUUID())
    }
}