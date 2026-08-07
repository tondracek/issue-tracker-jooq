package cz.developerthomas.issuetrackerjooq.taskcomment.domain

import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.user.domain.UserId

data class CreateTaskComment(
    val id: TaskCommentId,
    val content: String,
    val authorId: UserId,
    val taskId: TaskId,
)

