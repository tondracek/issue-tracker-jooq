package cz.developerthomas.issuetrackerjooq.task.view

import cz.developerthomas.issuetrackerjooq.enums.TaskPriority
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.view.TaskCommentDetailView
import cz.developerthomas.issuetrackerjooq.user.view.UserPreview
import java.time.LocalDateTime

data class TaskDetailView(
    val id: TaskId,
    val title: String,
    val description: String? = null,
    val assignee: UserPreview? = null,
    val reporter: UserPreview,
    val status: TaskStatus? = null,
    val priority: TaskPriority,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val comments: List<TaskCommentDetailView>,
)
