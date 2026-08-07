package cz.developerthomas.issuetrackerjooq.task.dto

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.CreateTask
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.user.domain.UserId

data class CreateTaskRequest(
    val title: String,
    val description: String?,
    val assigneeId: UserId?,
    val priority: TaskPriority,
)

fun CreateTaskRequest.toCommand(
    id: TaskId,
    reporterId: UserId,
    status: TaskStatus,
) = CreateTask(
    id = id,
    title = title,
    description = description,
    assigneeId = assigneeId,
    reporterId = reporterId,
    status = status,
    priority = priority,
)