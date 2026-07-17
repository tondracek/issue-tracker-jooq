package cz.developerthomas.issuetrackerjooq.task.dto

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.query.CreateTaskCommand
import cz.developerthomas.issuetrackerjooq.user.domain.UserId

data class CreateTaskRequest(
    val title: String,
    val description: String?,
    val assigneeId: UserId?,
    val reporterId: UserId,
    val priority: TaskPriority,
)

fun CreateTaskRequest.toCommand(
    id: TaskId,
    status: TaskStatus,
) = CreateTaskCommand(
    id = id,
    title = title,
    description = description,
    assigneeId = assigneeId,
    reporterId = reporterId,
    status = status,
    priority = priority,
)