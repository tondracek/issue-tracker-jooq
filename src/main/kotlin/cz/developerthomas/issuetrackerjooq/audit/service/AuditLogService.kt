package cz.developerthomas.issuetrackerjooq.audit.service

import cz.developerthomas.issuetrackerjooq.audit.domain.AuditLogId
import cz.developerthomas.issuetrackerjooq.audit.domain.CreateAuditLog
import cz.developerthomas.issuetrackerjooq.audit.query.CreateAuditLogCommand
import cz.developerthomas.issuetrackerjooq.auth.usecase.GetLoggedUserUC
import cz.developerthomas.issuetrackerjooq.enums.AuditEvent
import cz.developerthomas.issuetrackerjooq.task.domain.Task
import org.springframework.stereotype.Service
import tools.jackson.databind.JsonNode
import tools.jackson.databind.ObjectMapper
import java.util.*

@Service
class AuditLogService(
    private val createAuditLogCommand: CreateAuditLogCommand,
    private val getLoggedUserUC: GetLoggedUserUC,
    private val objectMapper: ObjectMapper,
) {

    fun taskCreated(task: Task) = log(
        task.id.value,
        task.title,
        AuditEvent.TASK_CREATED,
        objectMapper.valueToTree(task)
    )

    private fun log(
        id: UUID,
        displayName: String,
        auditEvent: AuditEvent,
        payload: JsonNode
    ) = createAuditLogCommand(
        CreateAuditLog(
            id = AuditLogId.newId(),
            entityId = id,
            displayName = displayName,
            authorId = getLoggedUserUC().id,
            action = auditEvent,
            payload = payload
        )
    )
}