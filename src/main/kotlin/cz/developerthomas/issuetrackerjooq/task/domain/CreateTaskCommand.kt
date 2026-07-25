package cz.developerthomas.issuetrackerjooq.task.domain

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.tables.records.TaskRecord
import cz.developerthomas.issuetrackerjooq.user.domain.UserId

data class CreateTaskCommand(
    val id: TaskId,
    val title: String,
    val description: String?,
    val assigneeId: UserId?,
    val reporterId: UserId,
    val status: TaskStatus,
    val priority: TaskPriority,
)

fun CreateTaskCommand.toTaskRecord() = TaskRecord(
    id = id,
    title = title,
    description = description,
    assigneeId = assigneeId,
    reporterId = reporterId,
    status = status,
    priority = priority,
)