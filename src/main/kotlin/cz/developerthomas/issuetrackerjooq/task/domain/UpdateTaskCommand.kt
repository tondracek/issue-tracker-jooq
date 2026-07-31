package cz.developerthomas.issuetrackerjooq.task.domain

import cz.developerthomas.issuetrackerjooq.core.fieldupdate.FieldUpdate
import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.user.domain.UserId

/**
 * Domain command for task updates.
 */
data class UpdateTaskCommand(
    val title: FieldUpdate<String>,
    val description: FieldUpdate<String?>,
    val assigneeId: FieldUpdate<UserId?>,
    val reporterId: FieldUpdate<UserId>,
    val status: FieldUpdate<TaskStatus>,
    val priority: FieldUpdate<TaskPriority>,
)

