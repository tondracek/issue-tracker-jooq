package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.pojos.Task
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.exception.TaskNotFoundException
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class GetTaskQuery(
    private val dsl: DSLContext,
) {

    operator fun invoke(id: TaskId) = dsl.selectFrom(TASK)
        .where(TASK.ID.eq(id))
        .fetchOneInto(Task::class.java)
        ?: throw TaskNotFoundException(id)
}