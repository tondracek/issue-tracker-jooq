package cz.developerthomas.issuetrackerjooq.task.usecase

import cz.developerthomas.issuetrackerjooq.audit.service.AuditLogService
import cz.developerthomas.issuetrackerjooq.auth.usecase.GetLoggedUserUC
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.CreateTask
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.dto.CreateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.dto.toCommand
import cz.developerthomas.issuetrackerjooq.task.query.CreateTaskCommand
import cz.developerthomas.issuetrackerjooq.task.query.GetTaskDetailQuery
import cz.developerthomas.issuetrackerjooq.task.view.TaskDetailView
import cz.developerthomas.issuetrackerjooq.user.domain.UserValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CreateTaskUC(
    private val createTaskCommand: CreateTaskCommand,
    private val getTaskDetailQuery: GetTaskDetailQuery,
    private val userValidator: UserValidator,
    private val getLoggedUserId: GetLoggedUserUC,
    private val auditLogService: AuditLogService,
) {

    @Transactional
    operator fun invoke(taskRequest: CreateTaskRequest): TaskDetailView {
        val createTaskCommand = taskRequest.toCommand(
            id = TaskId(UUID.randomUUID()),
            reporterId = getLoggedUserId().id,
            status = TaskStatus.TODO,
        )

        validate(createTaskCommand)

        val task = createTaskCommand(createTaskCommand)
        auditLogService.taskCreated(task)

        return getTaskDetailQuery(task.id)
    }

    private fun validate(createTask: CreateTask) {
        userValidator.requireExists(createTask.reporterId)

        createTask.assigneeId
            ?.let { userValidator.requireExists(it) }
    }
}