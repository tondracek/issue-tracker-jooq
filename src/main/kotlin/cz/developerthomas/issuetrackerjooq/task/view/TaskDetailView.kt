package cz.developerthomas.issuetrackerjooq.task.view

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.view.TaskCommentDetailView
import cz.developerthomas.issuetrackerjooq.user.view.UserPreview
import java.time.LocalDateTime
import java.util.*

data class TaskDetailView(
    val id: TaskId,
    val title: String,
    val description: String?,
    val assignee: UserPreview?,
    val reporter: UserPreview,
    val status: TaskStatus,
    val priority: TaskPriority,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val comments: List<TaskCommentDetailView>
) {
    companion object {
        fun from(
            id: UUID,
            title: String,
            description: String?,
            assignee: UserPreview?,
            reporter: UserPreview,
            status: TaskStatus,
            priority: TaskPriority,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime
        ) = TaskDetailView(
            id = TaskId(id),
            title = title,
            description = description,
            assignee = assignee,
            reporter = reporter,
            status = status,
            priority = priority,
            createdAt = createdAt,
            updatedAt = updatedAt,
            comments = emptyList()
        )
    }
}