package cz.developerthomas.issuetrackerjooq.task.dto

import cz.developerthomas.issuetrackerjooq.core.fieldupdate.FieldUpdate
import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.UpdateTaskCommand
import cz.developerthomas.issuetrackerjooq.user.domain.UserId

/**
 * Request payload for updating a task.
 *
 * Fields are nullable so the endpoint can be used as a partial update.
 */
data class UpdateTaskRequest(
    val title: FieldUpdate<String> = FieldUpdate.Undefined,
    val description: FieldUpdate<String?> = FieldUpdate.Undefined,
    val reporterId: FieldUpdate<UserId> = FieldUpdate.Undefined,
    val assigneeId: FieldUpdate<UserId?> = FieldUpdate.Undefined,
    val status: FieldUpdate<TaskStatus> = FieldUpdate.Undefined,
    val priority: FieldUpdate<TaskPriority> = FieldUpdate.Undefined,
)

fun UpdateTaskRequest.toCommand() = UpdateTaskCommand(
    title = title,
    description = description,
    assigneeId = assigneeId,
    reporterId = reporterId,
    status = status,
    priority = priority,
)