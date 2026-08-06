package cz.developerthomas.issuetrackerjooq.task.usecase

import cz.developerthomas.issuetrackerjooq.audit.service.AuditLogService
import cz.developerthomas.issuetrackerjooq.auth.usecase.GetLoggedUserUC
import cz.developerthomas.issuetrackerjooq.core.fieldupdate.onValue
import cz.developerthomas.issuetrackerjooq.core.fieldupdate.onValueNotNull
import cz.developerthomas.issuetrackerjooq.task.domain.Task
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.domain.UpdateTaskCommand
import cz.developerthomas.issuetrackerjooq.task.domain.status.validateStatusTransition
import cz.developerthomas.issuetrackerjooq.task.dto.UpdateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.dto.toCommand
import cz.developerthomas.issuetrackerjooq.task.query.GetTaskDetailQuery
import cz.developerthomas.issuetrackerjooq.task.query.GetTaskQuery
import cz.developerthomas.issuetrackerjooq.task.query.UpdateTaskCommandHandler
import cz.developerthomas.issuetrackerjooq.task.view.TaskDetailView
import cz.developerthomas.issuetrackerjooq.user.domain.UserValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateTaskUC(
    private val updateTaskCommandHandler: UpdateTaskCommandHandler,
    private val getTaskQuery: GetTaskQuery,
    private val getTaskDetailQuery: GetTaskDetailQuery,
    private val userValidator: UserValidator,
    private val getLoggedUserUC: GetLoggedUserUC,
    private val auditLogService: AuditLogService,
) {

    @Transactional
    operator fun invoke(id: TaskId, request: UpdateTaskRequest): TaskDetailView {
        val updateTaskCommand = request.toCommand()

        val original = getTaskQuery(id)
        validate(original, updateTaskCommand)

        val updated = updateTaskCommandHandler(id, updateTaskCommand)
        auditLogService.taskUpdated(original, updated)

        return getTaskDetailQuery(id)
    }

    private fun validate(original: Task, updateTaskCommand: UpdateTaskCommand) {
        updateTaskCommand.assigneeId.onValueNotNull {
            userValidator.requireExists(it)
        }

        updateTaskCommand.reporterId.onValue {
            userValidator.requireExists(it)
        }

        updateTaskCommand.status.onValue {
            validateStatusTransition(
                currentUser = getLoggedUserUC(),
                originalTask = original,
                to = it
            )
        }
    }
}
