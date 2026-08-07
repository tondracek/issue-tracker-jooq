package cz.developerthomas.issuetrackerjooq.taskcomment.domain

import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import java.time.LocalDateTime

data class TaskComment(
    val id: TaskCommentId,
    val content: String,
    val authorId: UserId,
    val taskId: TaskId,
    val createdAt: LocalDateTime?,
)

