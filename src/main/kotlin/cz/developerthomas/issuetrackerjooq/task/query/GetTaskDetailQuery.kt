package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.Task
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.api.exception.TaskNotFoundException
import cz.developerthomas.issuetrackerjooq.task.view.TaskId
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class GetTaskDetailQuery(
    private val dsl: DSLContext,
) {

    operator fun invoke(id: TaskId) {

        val taskPojo: Task = dsl.selectFrom(TASK)
            .where(TASK.ID.eq(id.value))
            .fetchOneInto(Task::class.java)
            ?: throw TaskNotFoundException(id)
    }
}
