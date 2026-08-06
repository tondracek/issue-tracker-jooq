package cz.developerthomas.issuetrackerjooq.audit.query

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditLogId
import cz.developerthomas.issuetrackerjooq.audit.domain.CreateAuditLog
import cz.developerthomas.issuetrackerjooq.tables.records.AuditLogRecord
import cz.developerthomas.issuetrackerjooq.tables.references.AUDIT_LOG
import org.jooq.DSLContext
import org.jooq.JSONB
import org.springframework.stereotype.Repository
import tools.jackson.module.kotlin.jacksonObjectMapper

@Repository
class CreateAuditLogCommand(
    private val dsl: DSLContext,
) {

    operator fun invoke(command: CreateAuditLog): AuditLogId {
        val record = command.toRecord()

        dsl.insertInto(AUDIT_LOG)
            .set(record)
            .execute()

        return record.id
    }
}

private fun CreateAuditLog.toRecord() = AuditLogRecord(
    id = id,
    entityId = entityId,
    displayName = displayName,
    authorId = authorId,
    action = action,
    payload = jacksonObjectMapper().writeValueAsString(payload)
        .let { JSONB.valueOf(it) }
)