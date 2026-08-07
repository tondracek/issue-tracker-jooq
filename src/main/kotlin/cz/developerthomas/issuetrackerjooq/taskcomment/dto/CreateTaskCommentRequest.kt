package cz.developerthomas.issuetrackerjooq.taskcomment.dto

import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.CreateTaskComment
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskCommentId
import cz.developerthomas.issuetrackerjooq.user.domain.UserId
import jakarta.validation.constraints.NotBlank

data class CreateTaskCommentRequest(
    @NotBlank
    val content: String,
)

fun CreateTaskCommentRequest.toCommand(
    id: TaskCommentId,
    taskId: TaskId,
    authorId: UserId,
) = CreateTaskComment(
    id = id,
    content = content,
    authorId = authorId,
    taskId = taskId,
)

