package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.Task.Companion.TASK
import cz.developerthomas.issuetrackerjooq.tables.records.TaskRecord
import cz.developerthomas.issuetrackerjooq.task.domain.CreateTaskCommand
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class CreateTaskCommandHandler(
    private val dsl: DSLContext,
) {

    operator fun invoke(createTaskCommand: CreateTaskCommand): TaskId {
        val task = createTaskCommand.toTaskRecord()

        dsl.insertInto(TASK)
            .set(task)
            .execute()

        return task.id
    }
}

private fun CreateTaskCommand.toTaskRecord() = TaskRecord(
    id = id,
    title = title,
    description = description,
    assigneeId = assigneeId,
    reporterId = reporterId,
    status = status,
    priority = priority,
)