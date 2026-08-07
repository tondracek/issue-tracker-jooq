package cz.developerthomas.issuetrackerjooq.taskcomment.usecase

import cz.developerthomas.issuetrackerjooq.audit.service.AuditLogService
import cz.developerthomas.issuetrackerjooq.auth.usecase.GetLoggedUserUC
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.CreateTaskComment
import cz.developerthomas.issuetrackerjooq.taskcomment.domain.TaskCommentId
import cz.developerthomas.issuetrackerjooq.taskcomment.dto.CreateTaskCommentRequest
import cz.developerthomas.issuetrackerjooq.taskcomment.dto.toCommand
import cz.developerthomas.issuetrackerjooq.taskcomment.query.CreateTaskCommentCommand
import cz.developerthomas.issuetrackerjooq.taskcomment.query.GetTaskCommentQuery
import cz.developerthomas.issuetrackerjooq.taskcomment.view.TaskCommentDetailView
import cz.developerthomas.issuetrackerjooq.user.domain.UserValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CreateTaskCommentUC(
    private val createTaskCommentCommand: CreateTaskCommentCommand,
    private val getTaskCommentQuery: GetTaskCommentQuery,
    private val getLoggedUserUC: GetLoggedUserUC,
    private val userValidator: UserValidator,
    private val auditLogService: AuditLogService,
) {

    @Transactional
    operator fun invoke(taskId: TaskId, request: CreateTaskCommentRequest): TaskCommentDetailView {
        val createCommand = request.toCommand(
            id = TaskCommentId(UUID.randomUUID()),
            taskId = taskId,
            authorId = getLoggedUserUC().id,
        )
        validate(createCommand)

        val comment = createTaskCommentCommand(createCommand)
        auditLogService.taskCommentCreated(comment)

        return getTaskCommentQuery(comment.id)
    }

    private fun validate(createTaskComment: CreateTaskComment) {
        userValidator.requireExists(createTaskComment.authorId)
    }
}




