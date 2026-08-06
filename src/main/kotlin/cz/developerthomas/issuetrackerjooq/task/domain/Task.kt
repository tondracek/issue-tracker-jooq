package cz.developerthomas.issuetrackerjooq.task.domain

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import java.time.LocalDateTime

data class Task(
    val id: TaskId,
    val title: String,
    val description: String?,
    val assigneeId: UserId?,
    val reporterId: UserId,
    val status: TaskStatus,
    val priority: TaskPriority,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)