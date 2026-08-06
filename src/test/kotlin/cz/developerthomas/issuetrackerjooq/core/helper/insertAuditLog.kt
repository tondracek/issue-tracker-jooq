package cz.developerthomas.issuetrackerjooq.core.helper

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditLogId
import cz.developerthomas.issuetrackerjooq.enums.AuditEvent
import cz.developerthomas.issuetrackerjooq.tables.records.AuditLogRecord
import cz.developerthomas.issuetrackerjooq.tables.references.AUDIT_LOG
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import org.jooq.DSLContext
import org.jooq.JSONB
import java.util.*

fun DSLContext.insertAuditLog(
    id: AuditLogId = AuditLogId.newId(),
    entityId: UUID = UUID.randomUUID(),
    displayName: String? = "Entity Display Name",
    authorId: UserId = insertUser().id,
    action: AuditEvent = AuditEvent.TASK_CREATED,
    payload: JSONB = JSONB.valueOf("{}"),
): AuditLogRecord {
    val auditLog = AuditLogRecord(
        id = id,
        entityId = entityId,
        displayName = displayName,
        authorId = authorId,
        action = action,
        payload = payload,
    )

    return insertInto(AUDIT_LOG)
        .set(auditLog)
        .returning()
        .fetchSingle()
}

