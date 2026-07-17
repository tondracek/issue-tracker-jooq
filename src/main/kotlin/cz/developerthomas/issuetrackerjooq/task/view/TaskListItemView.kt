package cz.developerthomas.issuetrackerjooq.task.view

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.user.view.UserPreview
import java.time.LocalDateTime

data class TaskListItemView(
    val id: TaskId,
    val title: String,
    val assignee: UserPreview?,
    val status: TaskStatus,
    val priority: TaskPriority,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)