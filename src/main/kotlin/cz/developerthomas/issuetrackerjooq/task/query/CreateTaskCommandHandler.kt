package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.Task.Companion.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.CreateTaskCommand
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.domain.toTaskRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class CreateTaskCommandHandler(
    private val dsl: DSLContext,
) {

    operator fun invoke(createTaskCommand: CreateTaskCommand): TaskId {
        val task = createTaskCommand.toTaskRecord()

        return dsl.insertInto(TASK)
            .set(task)
            .returningResult(TASK.ID)
            .fetchSingle(TASK.ID)
            .let { requireNotNull(it) }
    }
}