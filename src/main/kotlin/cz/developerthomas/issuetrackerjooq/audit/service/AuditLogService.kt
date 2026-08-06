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
import tools.jackson.databind.node.ObjectNode
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


    private val updateIgnoredProperties: Set<String> = setOf(
        Task::updatedAt.name,
        Task::createdAt.name,
        Task::id.name
    )

    fun taskUpdated(
        original: Task,
        updated: Task,
    ) = log(
        entityId = updated.id.value,
        displayName = updated.title,
        auditEvent = AuditEvent.TASK_UPDATED,
        payload = getUpdatePayload(original, updated)
    )

    /**
     * Converts two JSONs of formats original {"x": 1, "y": "a"} and updated {"x": 2, "y": "a" }
     * to a { "x": { "original": 1, "updated": 2 } }
     */
    private fun <T> getUpdatePayload(
        originalObject: T,
        updatedObject: T,
    ): ObjectNode {
        val original: JsonNode = objectMapper.valueToTree(originalObject)
        val updated: JsonNode = objectMapper.valueToTree(updatedObject)

        val result = objectMapper.createObjectNode()

        original.properties().forEach { (key, originalValue) ->
            val updatedValue = updated[key]

            if (originalValue == updatedValue || updateIgnoredProperties.contains(key)) return@forEach

            val innerNode = objectMapper.createObjectNode()
            innerNode.set("original", originalValue)
            innerNode.set("updated", updatedValue)
            result.set(key, innerNode)
        }

        return result
    }

    // ---------------
    // ---------------

    private fun log(
        entityId: UUID,
        displayName: String,
        auditEvent: AuditEvent,
        payload: JsonNode
    ) = createAuditLogCommand(
        CreateAuditLog(
            id = AuditLogId.newId(),
            entityId = entityId,
            displayName = displayName,
            authorId = getLoggedUserUC().id,
            action = auditEvent,
            payload = payload
        )
    )
}