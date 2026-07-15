package cz.developerthomas.issuetrackerjooq.taskcomment.view

import cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskCommentId
import cz.developerthomas.issuetrackerjooq.user.view.UserPreview
import java.time.LocalDateTime

data class TaskCommentDetailView(
    val id: TaskCommentId,
    val content: String,
    val author: UserPreview,
    val createdAt: LocalDateTime? = null,
)