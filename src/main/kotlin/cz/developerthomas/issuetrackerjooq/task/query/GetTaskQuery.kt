package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.Task
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.exception.TaskNotFoundException
import org.jooq.DSLContext
import org.jooq.Records
import org.springframework.stereotype.Repository

@Repository
class GetTaskQuery(
    private val dsl: DSLContext,
) {

    operator fun invoke(id: TaskId) = dsl.select(
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
        .from(TASK)
        .where(TASK.ID.eq(id))
        .fetchOne(Records.mapping(::Task))
        ?: throw TaskNotFoundException(id)
}
