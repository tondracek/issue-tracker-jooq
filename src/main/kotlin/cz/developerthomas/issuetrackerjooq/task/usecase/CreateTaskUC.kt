package cz.developerthomas.issuetrackerjooq.task.usecase

import cz.developerthomas.issuetrackerjooq.enums.TaskStatus
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.dto.CreateTaskRequest
import cz.developerthomas.issuetrackerjooq.task.dto.toCommand
import cz.developerthomas.issuetrackerjooq.task.query.CreateTaskQuery
import cz.developerthomas.issuetrackerjooq.task.query.GetTaskDetailQuery
import cz.developerthomas.issuetrackerjooq.task.view.TaskDetailView
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateTaskUC(
    private val createTaskQuery: CreateTaskQuery,
    private val getTaskDetailQuery: GetTaskDetailQuery,
) {

    operator fun invoke(taskRequest: CreateTaskRequest): TaskDetailView {
        val newId = TaskId(UUID.randomUUID())
        val initialStatus = TaskStatus.TODO


        val createTaskCommand = taskRequest.toCommand(id = newId, status = initialStatus)
        val createdId = createTaskQuery(createTaskCommand)

        return getTaskDetailQuery(createdId)
    }
}