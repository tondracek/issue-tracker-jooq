package cz.developerthomas.issuetrackerjooq.task.usecase

import cz.developerthomas.issuetrackerjooq.auth.usecase.GetLoggedUserUC
import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.dto.CreateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.dto.toCommand
import cz.developerthomas.issuetrackerjooq.task.query.CreateTaskCommand
import cz.developerthomas.issuetrackerjooq.task.query.CreateTaskQuery
import cz.developerthomas.issuetrackerjooq.task.query.GetTaskDetailQuery
import cz.developerthomas.issuetrackerjooq.task.view.TaskDetailView
import cz.developerthomas.issuetrackerjooq.user.domain.UserValidator
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateTaskUC(
    private val createTaskQuery: CreateTaskQuery,
    private val getTaskDetailQuery: GetTaskDetailQuery,
    private val userValidator: UserValidator,
    private val getLoggedUserId: GetLoggedUserUC,
) {

    operator fun invoke(taskRequest: CreateTaskRequest): TaskDetailView {
        val createTaskCommand = taskRequest.toCommand(
            id = TaskId(UUID.randomUUID()),
            reporterId = getLoggedUserId().id,
            status = TaskStatus.TODO,
        )

        validate(createTaskCommand)

        val createdId = createTaskQuery(createTaskCommand)
        return getTaskDetailQuery(createdId)
    }

    private fun validate(
        createTaskCommand: CreateTaskCommand,
    ) {
        userValidator.requireExists(createTaskCommand.reporterId)

        createTaskCommand.assigneeId
            ?.let { userValidator.requireExists(it) }
    }
}