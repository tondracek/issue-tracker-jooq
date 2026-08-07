package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.records.TaskRecord
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.CreateTask
import cz.developerthomas.issuetrackerjooq.task.domain.Task
import org.jooq.DSLContext
import org.jooq.Records
import org.springframework.stereotype.Repository

@Repository
class CreateTaskCommand(
    private val dsl: DSLContext,
) {

    operator fun invoke(createTask: CreateTask): Task {
        val task = createTask.toTaskRecord()

        return dsl.insertInto(TASK)
            .set(task)
            .returningResult(
                TASK.ID,
                TASK.TITLE,
                TASK.DESCRIPTION,
                TASK.ASSIGNEE_ID,
                TASK.REPORTER_ID,
                TASK.STATUS,
                TASK.PRIORITY,
                TASK.CREATED_AT,
                TASK.UPDATED_AT,
            )
            .fetchSingle(Records.mapping(::Task))
    }
}

private fun CreateTask.toTaskRecord() = TaskRecord(
    id = id,
    title = title,
    description = description,
    assigneeId = assigneeId,
    reporterId = reporterId,
    status = status,
    priority = priority,
)