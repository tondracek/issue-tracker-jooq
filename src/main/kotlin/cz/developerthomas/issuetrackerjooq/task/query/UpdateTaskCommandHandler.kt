package cz.developerthomas.issuetrackerjooq.task.query

import cz.developerthomas.issuetrackerjooq.core.fieldupdate.FieldUpdate
import cz.developerthomas.issuetrackerjooq.tables.references.TASK
import cz.developerthomas.issuetrackerjooq.task.domain.Task
import cz.developerthomas.issuetrackerjooq.task.domain.TaskId
import cz.developerthomas.issuetrackerjooq.task.domain.UpdateTaskCommand
import org.jooq.*
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UpdateTaskCommandHandler(
    private val dsl: DSLContext,
) {

    operator fun invoke(id: TaskId, updateTaskCommand: UpdateTaskCommand) =
        dsl.update(TASK)
            .addValueIfSpecified(TASK.TITLE, updateTaskCommand.title)
            .addValueIfSpecified(TASK.DESCRIPTION, updateTaskCommand.description)
            .addValueIfSpecified(TASK.ASSIGNEE_ID, updateTaskCommand.assigneeId)
            .addValueIfSpecified(TASK.REPORTER_ID, updateTaskCommand.reporterId)
            .addValueIfSpecified(TASK.STATUS, updateTaskCommand.status)
            .addValueIfSpecified(TASK.PRIORITY, updateTaskCommand.priority)
            .set(TASK.UPDATED_AT, LocalDateTime.now())
            .where(TASK.ID.eq(id))
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


fun <R : Record, T> UpdateSetStep<R>.addValueIfSpecified(
    tableField: TableField<R, T>,
    field: FieldUpdate<T>,
) = when (field) {
    is FieldUpdate.Value<T> -> this.set(tableField, field.value)
    FieldUpdate.Undefined -> this
}
